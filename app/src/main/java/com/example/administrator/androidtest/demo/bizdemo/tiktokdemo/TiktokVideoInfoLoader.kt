package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo

import android.util.Log
import androidx.collection.LruCache
import com.bear.libcommon.executor.BgThreadExecutor
import com.bear.libcommon.executor.MainThreadExecutor
import com.bear.libcommon.util.IOUtil
import com.bear.libstorage.FileStorage
import com.example.administrator.androidtest.AndroidTestApplication
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.util.LinkedList

object TiktokVideoInfoLoader {
    private const val TAG = "TiktokVideoInfoLoader"
    private const val ITEM_PAGE_SIZE = 10
    private const val DETAIL_PAGE_SIZE = 5
    private const val RANGE_SIZE = 5

    private var lastRecIndex = 0
    private val sourceItemInfos: MutableList<TiktokItemInfo> = LinkedList()
    private val sourceDetailInfoMap = hashMapOf<Int, TiktokDetailInfo>()
    private val expiredDetailInfoMap = hashMapOf<Int, TiktokDetailInfo>()
    private val detailInfoCache = LruCache<Int, List<TiktokDetailInfo>>(100)

    private var loadFeed_1 = true

    fun refreshRecommendItemInfos(callback: (itemInfos: List<TiktokItemInfo>?) -> Unit) {
        loadRecommendItemInfosInner(0, ITEM_PAGE_SIZE) {
            if (it != null) {
                loadFeed_1 = !loadFeed_1
                sourceItemInfos.clear()
                sourceItemInfos.addAll(it)
                lastRecIndex = sourceItemInfos.size
            }
            callback(it)
        }
    }

    fun loadRecommendItemInfos(callback: (itemInfos: List<TiktokItemInfo>?) -> Unit) {
        loadRecommendItemInfosInner(lastRecIndex, ITEM_PAGE_SIZE) { itemInfos ->
            if (itemInfos != null) {
                var finalItemInfos = itemInfos
                sourceItemInfos.lastOrNull()?.let { lastItemInfo ->
                    var lastItemInfoId = lastItemInfo.id
                    finalItemInfos = itemInfos.map {
                        lastItemInfoId++
                        it.copy(id = lastItemInfoId)
                    }
                }
                finalItemInfos?.let { sourceItemInfos.addAll(it) }
                lastRecIndex = sourceItemInfos.size
            }
            callback(itemInfos)
        }
    }

    private fun loadRecommendItemInfosInner(recIndex: Int, recSize: Int = ITEM_PAGE_SIZE, callback: (itemInfos: List<TiktokItemInfo>?) -> Unit) {
        doInBgThread {
            var inputStream: InputStream? = null
            try {
                val assetName = getAssetName(recIndex)
                inputStream = AndroidTestApplication.context.assets.open(assetName)
                val typeToken = object : TypeToken<List<TiktokItemInfo>>() {}
                val detailInfos = FileStorage.readObjFromJson(inputStream, typeToken) ?: emptyList()
                doInMainThread {
                    callback(detailInfos)
                }
            } catch (e: Exception) {
                Log.e(TAG, "loadRecommendItemInfos: error msg = ${e.message}")
                doInMainThread {
                    callback(null)
                }
            } finally {
                IOUtil.close(inputStream)
            }
        }
    }

    fun loadDetailInfosByRange(recIndex: Int, callback: (detailInfos: List<TiktokDetailInfo>?) -> Unit) {
        if (recIndex !in sourceItemInfos.indices) {
            callback(emptyList())
            return
        }
        val idList = arrayListOf<Int>()
        val startRange = maxOf(0, recIndex - RANGE_SIZE)
        val endRange = minOf(sourceItemInfos.size - 1, recIndex + RANGE_SIZE)
        for (i in startRange..endRange) {
            idList.add(sourceItemInfos[i].id)
        }
        loadDetailInfosByIdsInner(idList, callback)
    }

    fun loadPreRecommendDetailInfos(recIndex: Int, recSize: Int = DETAIL_PAGE_SIZE, callback: (itemInfos: List<TiktokDetailInfo>?) -> Unit) {
        // 1. 定义本次请求需要的数据范围（向前加载）
        // 例如，如果 recIndex 是 20，DETAIL_PAGE_SIZE 是 5，范围就是 16..20
        val startIndex = maxOf(0, recIndex - DETAIL_PAGE_SIZE + 1)
        val requestedRange = startIndex..recIndex

        // 2. 辅助函数：从缓存中构建并返回最终结果 (逻辑不变)
        fun serveFromCache() {
            val result = requestedRange.mapNotNull { sourceDetailInfoMap[it] }
            callback(result)
        }

        // 3. 检查数据是否已完全缓存 (逻辑不变)
        if (requestedRange.all { sourceDetailInfoMap.containsKey(it) }) {
            serveFromCache()
            return
        }

        // 4. 找到需要开始网络请求的结束索引（向前查找）
        var fetchEndIndex = recIndex
        while (sourceDetailInfoMap.contains(fetchEndIndex) && fetchEndIndex > 0) {
            fetchEndIndex--
        }
        // 计算需要获取的数据块的起始索引
        val fetchStartIndex = maxOf(0, fetchEndIndex - DETAIL_PAGE_SIZE + 1)

        // 5. 判断“基础信息”是否已存在
        // 注意：与加载“下一页”不同，我们通常没有机制去网络加载“上一页”的基础信息并将其插入到列表头部。
        // 因此，我们只能在已有的 sourceItemInfos 范围内获取详情。
        if (fetchStartIndex in sourceItemInfos.indices) {
            // 基础信息充足，只需加载详情
            val itemsToFetch = sourceItemInfos.subList(fetchStartIndex, fetchEndIndex + 1)
            loadDetailInfosByIdsInner(itemsToFetch.map { it.id }) { detailInfos ->
                // 将获取到的详情信息与其在 sourceItemInfos 中的索引关联并存入缓存 (逻辑不变)
                detailInfos?.forEach { detail ->
                    val index = sourceItemInfos.indexOfFirst { it.id == detail.id }
                    if (index != -1) {
                        sourceDetailInfoMap[index] = detail
                    }
                }
                // 缓存更新后，再次尝试从缓存中提供数据
                serveFromCache()
            }
        } else {
            // 基础信息不足，并且我们没有向前加载基础信息的机制，因此请求失败。
            // 这种情况下可以返回 null，或者返回缓存中已有的部分数据。
            // 为了与 loadNext... 的失败情况保持一致，我们返回 null。
            callback(null)
        }
    }

    fun loadNextRecommendDetailInfos(recIndex: Int, recSize: Int = DETAIL_PAGE_SIZE, callback: (itemInfos: List<TiktokDetailInfo>?) -> Unit) {
        // 定义本次请求需要的数据范围
        val requestedRange = recIndex until (recIndex + DETAIL_PAGE_SIZE)

        // 辅助函数：从缓存中构建并返回最终结果
        fun serveFromCache() {
            val result = requestedRange.mapNotNull { sourceDetailInfoMap[it] }
            callback(result)
        }

        // 检查数据是否已完全缓存
        if (requestedRange.all { sourceDetailInfoMap.containsKey(it) }) {
            serveFromCache()
            return
        }

        // 找到需要开始网络请求的起始索引
        var fetchStartIndex = recIndex
        while (sourceDetailInfoMap.contains(fetchStartIndex)) {
            fetchStartIndex++
        }
        val fetchEndIndex = fetchStartIndex + DETAIL_PAGE_SIZE

        // 判断是加载“详情”，还是需要先加载“基础信息”
        if (fetchEndIndex <= sourceItemInfos.size) {
            //  基础信息充足，只需加载详情
            val itemsToFetch = sourceItemInfos.subList(fetchStartIndex, fetchEndIndex)
            loadDetailInfosByIdsInner(itemsToFetch.map { it.id }) { detailInfos ->
                // 将获取到的详情信息与其在 sourceItemInfos 中的索引关联并存入缓存
                detailInfos?.forEach { detail ->
                    val index = sourceItemInfos.indexOfFirst { it.id == detail.id }
                    if (index != -1) {
                        sourceDetailInfoMap[index] = detail
                    }
                }
                // 缓存更新后，再次尝试从缓存中提供数据
                serveFromCache()
            }
        } else {
            // 基础信息不足，需要先获取更多基础信息
            loadRecommendItemInfos { newItems ->
                if (newItems.isNullOrEmpty()) {
                    // 无法获取更多基础信息，请求失败
                    callback(null)
                } else {
                    // 通过递归调用，让函数以最新的数据状态重新执行一遍逻辑
                    // 这比在回调中重复写处理逻辑要简洁得多
                    loadNextRecommendDetailInfos(recIndex, recSize, callback)
                }
            }
        }
    }

    fun loadDetailInfosByIds(videoIds: List<Int>, callback: (detailInfos: List<TiktokDetailInfo>?) -> Unit) {
        loadDetailInfosByIdsInner(videoIds, callback)
    }

    private fun loadDetailInfosByIdsInner(videoIds: List<Int>, callback: (detailInfos: List<TiktokDetailInfo>?) -> Unit) {
        doInBgThread {
            Thread.sleep(1000)
            val detailInfos = sourceItemInfos.filter {
                videoIds.contains(it.id)
            }.map {
                it.toDetailInfo()
            }
            doInMainThread {
                callback(detailInfos)
            }
        }
    }

    private fun getAssetName(pageNum: Int): String {
        return if (loadFeed_1) {
            if (pageNum == 0) {
                "tiktok/feed_1/video_refresh_item_info.json"
            } else {
                "tiktok/feed_1/video_load_more_item_info.json"
            }
        } else {
            if (pageNum == 0) {
                "tiktok/feed_2/video_refresh_item_info.json"
            } else {
                "tiktok/feed_2/video_load_more_item_info.json"
            }
        }
    }

    private fun doInBgThread(callback: () -> Unit) {
        BgThreadExecutor.execute {
            callback()
        }
    }

    private fun doInMainThread(callback: () -> Unit) {
        MainThreadExecutor.post {
            callback()
        }
    }

    fun getItemInfo(index: Int): TiktokItemInfo? {
        return sourceItemInfos.getOrNull(index)
    }

    fun getDetailInfo(index: Int): TiktokDetailInfo? {
        return sourceDetailInfoMap.get(index)
    }

    fun getInitialDetailInfos(): List<TiktokDetailInfo> {
        return sourceItemInfos.map { it.toDetailInfo() }
    }
}
