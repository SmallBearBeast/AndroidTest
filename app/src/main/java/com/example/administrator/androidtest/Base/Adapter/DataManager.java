package com.example.administrator.androidtest.Base.Adapter;

import com.example.administrator.androidtest.Common.Util.Core.CollectionUtil;
import com.example.administrator.androidtest.Log.SLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理具体的add，remove，update操作。
 */
public class DataManager {

    private static final String TAG = "DataManager";

    // TODO: 2019-06-22 data数据保护
    private List mProviderDatas = new ArrayList();
    private VHAdapter mAdapter;

    public void setAdapter(VHAdapter adapter){
        mAdapter = adapter;
    }

    public <D> void setData(List<D> datas) {
        if(CollectionUtil.isEmpty(datas)){
            SLog.d(TAG, "setData: datas is empty");
            return;
        }
        if(!mAdapter.isRegister(datas.get(0))){
            SLog.d(TAG, "setData: datas is not registered");
            return;
        }
        mProviderDatas.clear();
        mProviderDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    public <D> void addLast(D data){
        add(mProviderDatas.size(), data);
    }

    public <D> void add(int index, D data){
        if(!mAdapter.isRegister(data)){
            SLog.d(TAG, "add: data is not registered");
            return;
        }
        if(index == size()){
            mProviderDatas.add(data);
            mAdapter.notifyItemRangeInserted(index, 1);
            return;
        }
        if(!checkIndex(index)){
            SLog.d(TAG, "add: index is out of range");
            return;
        }
        mProviderDatas.add(index, data);
        mAdapter.notifyItemRangeInserted(index, 1);
    }

    public <D> void addLast(List<D> datas){
        add(mProviderDatas.size(), datas);
    }

    public <D> void add(int index, List<D> datas){
        if(CollectionUtil.isEmpty(datas)){
            SLog.d(TAG, "add: datas is empty");
            return;
        }
        if(!mAdapter.isRegister(datas.get(0))){
            SLog.d(TAG, "add: datas is not registered");
            return;
        }
        if(index == size()){
            mProviderDatas.addAll(datas);
            mAdapter.notifyItemRangeInserted(index, datas.size());
            return;
        }
        if(!checkIndex(index)){
            SLog.d(TAG, "add: index is out of range");
            return;
        }
        mProviderDatas.addAll(index, datas);
        mAdapter.notifyItemRangeInserted(index, datas.size());
    }

    public <D> void addFirst(D data){
        add(0, data);
    }

    public <D> void addFirst(List<D> datas){
        add(0, datas);
    }

    public <D> void remove(D... datas){
        if(datas.length > 0) {
            for (D d : datas) {
                remove(findIndexInArray(d), 1);
            }
        }
    }

    public <D> void remove(List<D> datas){
        if(!CollectionUtil.isEmpty(datas)){
            for (D d : datas) {
                remove(findIndexInArray(d), 1);
            }
        }
    }

    public void remove(int index, int num){
        if(num > 0 && index >= 0 && index + num <= size()){
            mProviderDatas.subList(index, num + index).clear();
            mAdapter.notifyItemRangeRemoved(index, num);
        }
    }

    public void removeFirst(int num){
        remove(0, num);
    }

    public void removeLast(int num){
        remove(mProviderDatas.size() - num, num);
    }

    public <D> void update(D d){
        if(d != null){
            update(findIndexInArray(d), d, null);
        }
    }

    public <D> void update(D d, Notify notify){
        if(d != null){
            update(findIndexInArray(d), d, notify);
        }
    }

    public <D> void update(int index, D d, Notify notify){
        if(!checkIndex(index)){
            SLog.d(TAG, "update: index is out of range");
            return;
        }
        if(d != null){
            mProviderDatas.set(index, d);
            mAdapter.notifyItemChanged(index, notify);
        }
    }

    public void move(int fromPos, int toPos){
        if(!checkIndex(fromPos)){
            SLog.d(TAG, "move: fromPos is out of range fromPos = " + fromPos);
            return;
        }
        if(!checkIndex(toPos)){
            SLog.d(TAG, "move: toPos is out of range toPos = " + toPos);
            return;
        }
        Object fromData = mProviderDatas.get(fromPos);
        Object toData = mProviderDatas.get(toPos);
        mProviderDatas.set(toPos, fromData);
        mProviderDatas.set(fromPos, toData);
        mAdapter.notifyItemMoved(fromPos, toPos);
    }

    private <D> int findIndexInArray(D data) {
        for (int i = 0, len = mProviderDatas.size(); i < len; i++) {
            if(mProviderDatas.get(i).equals(data)){
                return i;
            }
        }
        return -1;
    }

    public int size(){
        return mProviderDatas.size();
    }

    public Object get(int position){
        if(position >= 0 && position < size()){
            return mProviderDatas.get(position);
        }
        return null;
    }

    private boolean checkIndex(int index){
        return index >= 0 && index < size();
    }

    public List getData(){
        return mProviderDatas;
    }
}
