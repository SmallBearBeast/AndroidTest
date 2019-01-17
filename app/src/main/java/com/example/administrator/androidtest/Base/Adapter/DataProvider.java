package com.example.administrator.androidtest.Base.Adapter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 将数据包装为Data类型，多了一个type，根据列表顺序依次添加到DataProvider
 */
public class DataProvider {

    public List<Data> mProviderDatas = new ArrayList();


    public void add(@NonNull Data... datas){
        mProviderDatas.addAll(Arrays.asList(datas));
    }

    public void add(@NonNull List<Data> datas){
        mProviderDatas.addAll(datas);
    }

    public int getItemViewType(int position) {
        return mProviderDatas.get(position).getType();
    }

    public int size(){
        return mProviderDatas.size();
    }

    public Object get(int position){
        return mProviderDatas.get(position);
    }


    public static class Data<T>{
        private T mData;

        private int mType;

        public Data(T data, int type){
            if(data != null){
                mData = data;
                mType = type;
            }
        }

        public T getData(){
            return mData;
        }

        public int getType(){
            return mType;
        }
    }
}
