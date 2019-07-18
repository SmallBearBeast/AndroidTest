package com.example.administrator.androidtest.Base.PageShareData;

import android.util.Log;
import android.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: 2019-06-13 不保留活动有bug
public class PageShareDataHelper {
    private static final String TAG = "PageShareDataHelper";
    private static AtomicInteger mIndex = new AtomicInteger();
    private Map<PageKey, Object> mShareDataMap = new HashMap<>();
    private LinkedList<Pair<PageKey, LinkedList<PageKey>>> mPageKeyList = new LinkedList<>();

    private static class SingleTon{
        static PageShareDataHelper sPageShareDataHelper = new PageShareDataHelper();
    }

    public static PageShareDataHelper getInstance(){
        return PageShareDataHelper.SingleTon.sPageShareDataHelper;
    }

    public void markNewPage(PageKey pageKey){
        Log.d(TAG, "markNewPage: ");
        LinkedList<PageKey> pageKeyList = new LinkedList<>();
        mPageKeyList.add(new Pair<>(pageKey, pageKeyList));
    }

    public void updateData(String page, Object obj){

    }

    public void addData(String page, Object obj){
        Log.d(TAG, "addData: page = " + page + " obj == null: " + (obj == null));
        PageKey pageKey = createPageKey(page);
        LinkedList<PageKey> curPageKeyList = getCurPageKeyList();
        if(curPageKeyList != null){
            curPageKeyList.add(pageKey);
            mShareDataMap.put(pageKey, obj);
        }
    }

    public <T> T getData(String page){
        try{
            LinkedList<PageKey> curPageKeyList = getCurPageKeyList();
            PageKey pageKey = null;
            if(curPageKeyList != null) {
                for (PageKey key : curPageKeyList) {
                    if (key.mPage.equals(page)) {
                        pageKey = key;
                        break;
                    }
                }
            }
            T data = (T) mShareDataMap.get(pageKey);
            Log.d(TAG, "getData: page = " + page + " data == null: " + (data == null));
            return data;
        }catch (Exception e){
            return null;
        }
    }

    // TODO: 2019-06-13 不保留活动有崩溃，需要一个之前生成的PageKey清除数据
    public void clear(PageKey pageKey){
        Log.d(TAG, "clear: ");
        int index = -1;
        for (int i = 0, size = mPageKeyList.size(); i < size; i++) {
            Pair<PageKey, LinkedList<PageKey>> pair = mPageKeyList.get(i);
            if(pageKey.equals(pair.first)){
                for (PageKey key : pair.second) {
                    mShareDataMap.remove(key);
                }
                index = i;
                break;
            }
        }
        if(index != -1){
            mPageKeyList.remove(index);
        }
    }

    public static PageKey createPageKey(String page){
        return new PageKey(page, mIndex.incrementAndGet());
    }

    private LinkedList<PageKey> getCurPageKeyList(){
        if(!mPageKeyList.isEmpty()){
            return mPageKeyList.getLast().second;
        }
        return null;
    }
}
