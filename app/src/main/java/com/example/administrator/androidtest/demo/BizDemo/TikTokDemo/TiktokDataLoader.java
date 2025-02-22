package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo;

import android.util.Range;

import androidx.collection.LruCache;

import com.bear.libstorage.FileStorage;
import com.example.administrator.androidtest.AndroidTestApplication;
import com.example.libbase.Executor.BgThreadExecutor;
import com.example.libbase.Executor.MainThreadExecutor;
import com.example.libbase.Util.IOUtil;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TiktokDataLoader {

    private static final int VIDEO_PAGE_SIZE = 10;
    private static final int VIDEO_DETAIL_PAGE_SIZE = 10;
    private static final int RANGE_SIZE = 5;

    private int lastItemIndex = 0;
    private Range<Integer> videoDetailLoaderRange = Range.create(0, 0);
    private final List<TiktokVideoInfo> sourceTiktokDataList = new LinkedList<>();
    private final LruCache<Integer, List<TiktokVideoDetailInfo>> tiktokVideoDetailInfoCache = new LruCache<>(100);

    public void refreshTikTokVideoInfoList(TiktokVideoInfoCallback callback) {
        loadTikTokVideoInfoList(0, VIDEO_PAGE_SIZE, tiktokBeanList -> {
            sourceTiktokDataList.clear();
            sourceTiktokDataList.addAll(tiktokBeanList);
            lastItemIndex = tiktokBeanList.size();
            if (callback != null) {
                MainThreadExecutor.post(() -> callback.onDataLoaded(tiktokBeanList));
            }
        });
    }

    public void loadMoreTikTokVideoInfoList(TiktokVideoInfoCallback callback) {
        loadTikTokVideoInfoList(lastItemIndex, VIDEO_PAGE_SIZE, tiktokBeanList -> {
            sourceTiktokDataList.addAll(tiktokBeanList);
            lastItemIndex = lastItemIndex + tiktokBeanList.size();
            if (callback != null) {
                MainThreadExecutor.post(() -> callback.onDataLoaded(tiktokBeanList));
            }
        });
    }

    private void loadTikTokVideoInfoList(int startIndex, int pageSize, TiktokVideoInfoCallback callback) {
        if (startIndex == 0) {
            BgThreadExecutor.execute(() -> {
                InputStream inputStream = null;
                try {
                    inputStream = AndroidTestApplication.getContext().getAssets().open("tiktok_video_info.json");
                    TypeToken<List<TiktokVideoInfo>> typeToken = new TypeToken<List<TiktokVideoInfo>>() {
                    };
                    List<TiktokVideoInfo> tiktokVideoInfoList = FileStorage.readObjFromJson(inputStream, typeToken);
                    if (tiktokVideoInfoList != null) {
                        if (callback != null) {
                            callback.onDataLoaded(tiktokVideoInfoList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtil.close(inputStream);
                }
            });
        } else {
            BgThreadExecutor.execute(() -> {
                InputStream inputStream = null;
                try {
                    inputStream = AndroidTestApplication.getContext().getAssets().open("tiktok_loadmore_video_info.json");
                    TypeToken<List<TiktokVideoInfo>> typeToken = new TypeToken<List<TiktokVideoInfo>>() {
                    };
                    List<TiktokVideoInfo> tiktokVideoInfoList = FileStorage.readObjFromJson(inputStream, typeToken);
                    if (tiktokVideoInfoList != null) {
                        if (callback != null) {
                            callback.onDataLoaded(tiktokVideoInfoList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtil.close(inputStream);
                }
            });
        }
    }

    public void loadTiktokVideoDetailInfoByRange(int startIndex, TiktokVideoDetailInfoCallback callback) {
        List<Integer> idList = new ArrayList<>();
        if (startIndex >= 0 && startIndex < sourceTiktokDataList.size()) {
            idList.add(sourceTiktokDataList.get(startIndex).id);
            for (int i = startIndex + 1; i <= startIndex + RANGE_SIZE && i < sourceTiktokDataList.size(); i++) {
                idList.add(sourceTiktokDataList.get(i).id);
            }
            for (int i = startIndex - 1; i >= startIndex - RANGE_SIZE && i >= 0; i++) {
                idList.add(sourceTiktokDataList.get(i).id);
            }
        }
        loadTiktokVideoDetailInfoByIds(idList, callback);
    }

    private void loadTiktokVideoDetailInfoByIds(List<Integer> idList, TiktokVideoDetailInfoCallback callback) {
        loadTikTokVideoDetailInfo(0, VIDEO_DETAIL_PAGE_SIZE, videoDetailInfoList -> {
            for (TiktokVideoDetailInfo detailInfo : videoDetailInfoList) {
                int findId = -1;
                for (int i = 0; i < sourceTiktokDataList.size(); i++) {
                    if (sourceTiktokDataList.get(i).id == detailInfo.id) {
                        findId = i;
                        break;
                    }
                }
                if (findId != -1) {
                    sourceTiktokDataList.set(findId, detailInfo.toTiktokVideoInfo());
                }
            }
            if (callback != null) {
                MainThreadExecutor.post(() -> callback.onDataLoaded(videoDetailInfoList));
            }
        });
    }

    public void refreshVideoDetailInfoList(TiktokVideoDetailInfoCallback callback) {
        loadTikTokVideoDetailInfo(0, VIDEO_DETAIL_PAGE_SIZE, videoDetailInfoList -> {
            sourceTiktokDataList.clear();
            for (TiktokVideoDetailInfo detailInfo : videoDetailInfoList) {
                sourceTiktokDataList.add(detailInfo.toTiktokVideoInfo());
            }
            lastItemIndex = videoDetailInfoList.size();
            if (callback != null) {
                MainThreadExecutor.post(() -> callback.onDataLoaded(videoDetailInfoList));
            }
        });
    }
    public void loadMoreVideoDetailInfoList(TiktokVideoDetailInfoCallback callback) {
        loadTikTokVideoDetailInfo(lastItemIndex, VIDEO_DETAIL_PAGE_SIZE, videoDetailInfoList -> {
            for (TiktokVideoDetailInfo detailInfo : videoDetailInfoList) {
                sourceTiktokDataList.add(detailInfo.toTiktokVideoInfo());
            }
            lastItemIndex = lastItemIndex + videoDetailInfoList.size();
            if (callback != null) {
                MainThreadExecutor.post(() -> callback.onDataLoaded(videoDetailInfoList));
            }
        });
    }

    private void loadTikTokVideoDetailInfo(int startIndex, int pageSize, TiktokVideoDetailInfoCallback callback) {
        if (startIndex == 0) {
            BgThreadExecutor.execute(() -> {
                InputStream inputStream = null;
                try {
                    inputStream = AndroidTestApplication.getContext().getAssets().open("tiktok_video_detail_info.json");
                    TypeToken<List<TiktokVideoDetailInfo>> typeToken = new TypeToken<List<TiktokVideoDetailInfo>>() {
                    };
                    List<TiktokVideoDetailInfo> videoDetailInfoList = FileStorage.readObjFromJson(inputStream, typeToken);
                    if (videoDetailInfoList != null) {
                        if (callback != null) {
                            callback.onDataLoaded(videoDetailInfoList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtil.close(inputStream);
                }
            });
        } else {
            BgThreadExecutor.execute(() -> {
                InputStream inputStream = null;
                try {
                    inputStream = AndroidTestApplication.getContext().getAssets().open("tiktok_loadmore_video_detail_info.json");
                    TypeToken<List<TiktokVideoDetailInfo>> typeToken = new TypeToken<List<TiktokVideoDetailInfo>>() {
                    };
                    List<TiktokVideoDetailInfo> videoDetailInfoList = FileStorage.readObjFromJson(inputStream, typeToken);
                    if (videoDetailInfoList != null) {
                        if (callback != null) {
                            callback.onDataLoaded(videoDetailInfoList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtil.close(inputStream);
                }
            });
        }
    }

    public List<TiktokVideoInfo> getSourceTiktokDataList() {
        return sourceTiktokDataList;
    }

    public List<TiktokVideoDetailInfo> getSourceTiktokVideoDetailList() {
        List<TiktokVideoDetailInfo> detailInfoList = new ArrayList<>(sourceTiktokDataList.size());
        for (TiktokVideoInfo videoInfo : sourceTiktokDataList) {
            detailInfoList.add(videoInfo.toTiktokVideoDetailInfo());
        }
        return detailInfoList;
    }

    private static class SingleTon {
        private static final TiktokDataLoader Instance = new TiktokDataLoader();
    }

    public static TiktokDataLoader getInstance() {
        return TiktokDataLoader.SingleTon.Instance;
    }

    public interface TiktokVideoDetailInfoCallback {
        void onDataLoaded(List<TiktokVideoDetailInfo> tiktokVideoDetailInfoList);
    }

    public interface TiktokVideoInfoCallback {
        void onDataLoaded(List<TiktokVideoInfo> tiktokVideoInfoList);
    }
}
