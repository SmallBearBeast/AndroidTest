package com.example.libframework.Rv;

import android.database.Cursor;
import com.example.libbase.Util.CollectionUtil;
import com.example.liblog.SLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理具体的add，remove，update操作。
 * Cursor is only read
 */
@SuppressWarnings("unchecked")
public class DataManager {

    private static final String TAG = "DataManager";

    // TODO: 2019-06-22 data数据保护
    private List mProviderDatas = new ArrayList();
    private Map<Integer, Cursor> mIndexWithCursorMap = new HashMap<>();
    private VHAdapter mAdapter;

    public void setAdapter(VHAdapter adapter) {
        mAdapter = adapter;
    }

    public <D> void setData(List<D> datas) {
        if (CollectionUtil.isEmpty(datas)) {
            SLog.d(TAG, "setData: datas is empty");
            return;
        }
        if (!mAdapter.isRegister(datas.get(0))) {
            SLog.d(TAG, "setData: datas is not registered");
            return;
        }
        mProviderDatas.clear();
        mProviderDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    public void addCursor(int index, Cursor cursor) {
        if (cursor != null && cursor.getCount() == 0) {
            SLog.d(TAG, "addCursor: cursor count is 0");
            return;
        }
        if (!mAdapter.isRegister(cursor)) {
            SLog.d(TAG, "addCursor: cursor is not registered");
            return;
        }
        if (index == size()) {
            mProviderDatas.add(cursor);
            mIndexWithCursorMap.put(index, cursor);
            mAdapter.notifyItemRangeInserted(index, cursor.getCount());
            return;
        }
        if (!checkIndex(index)) {
            SLog.d(TAG, "addCursor: index is out of range");
            return;
        }
        mProviderDatas.add(index, cursor);
        mIndexWithCursorMap.put(index, cursor);
        mAdapter.notifyItemRangeInserted(index, cursor.getCount());
    }

    public void addCursorFirst(Cursor cursor) {
        addCursor(0, cursor);
    }

    public void addCursorLast(Cursor cursor) {
        addCursor(size(), cursor);
    }

    public <D> void addLast(D data) {
        add(size(), data);
    }

    public <D> void add(int index, D data) {
        if (!mAdapter.isRegister(data)) {
            SLog.d(TAG, "add: data is not registered");
            return;
        }
        if (index == size()) {
            mProviderDatas.add(data);
            mAdapter.notifyItemRangeInserted(index, 1);
            return;
        }
        if (!checkIndex(index)) {
            SLog.d(TAG, "add: index is out of range");
            return;
        }
        mProviderDatas.add(index, data);
        mAdapter.notifyItemRangeInserted(index, 1);
    }

    public <D> void addLast(List<D> datas) {
        add(size(), datas);
    }

    public <D> void add(int index, List<D> datas) {
        if (CollectionUtil.isEmpty(datas)) {
            SLog.d(TAG, "add: datas is empty");
            return;
        }
        if (!mAdapter.isRegister(datas.get(0))) {
            SLog.d(TAG, "add: datas is not registered");
            return;
        }
        if (index == size()) {
            mProviderDatas.addAll(datas);
            mAdapter.notifyItemRangeInserted(index, datas.size());
            return;
        }
        if (!checkIndex(index)) {
            SLog.d(TAG, "add: index is out of range");
            return;
        }
        mProviderDatas.addAll(index, datas);
        mAdapter.notifyItemRangeInserted(index, datas.size());
    }

    public <D> void addFirst(D data) {
        add(0, data);
    }

    public <D> void addFirst(List<D> datas) {
        add(0, datas);
    }

    // TODO: 2019-10-20 need removeCursor and upadteCursor method
    public <D> void remove(D... datas) {
        if (datas.length > 0) {
            for (D d : datas) {
                remove(findIndexInArray(d), 1);
            }
        }
    }

    public <D> void remove(List<D> datas) {
        if (!CollectionUtil.isEmpty(datas)) {
            for (D d : datas) {
                remove(findIndexInArray(d), 1);
            }
        }
    }

    public void remove(int index, int num) {
        if (num > 0 && index >= 0 && index + num <= size()) {
            mProviderDatas.subList(index, num + index).clear();
            mAdapter.notifyItemRangeRemoved(index, num);
        }
        resetIndexWithCursorMap();
    }

    public void removeFirst(int num) {
        remove(0, num);
    }

    public void removeLast(int num) {
        remove(mProviderDatas.size() - num, num);
    }

    public <D> void update(D d) {
        if (d != null) {
            update(findIndexInArray(d), d, null);
        }
    }

    public <D> void update(D d, Notify notify) {
        if (d != null) {
            update(findIndexInArray(d), d, notify);
        }
    }

    public <D> void update(int index, D d, Notify notify) {
        if (!checkIndex(index)) {
            SLog.d(TAG, "update: index is out of range");
            return;
        }
        if (d != null) {
            mProviderDatas.set(index, d);
            mAdapter.notifyItemChanged(index, notify);
        }
    }

    // TODO: 2019-07-16 move notifyItemMoved有问题，先使用notifyItemRangeChanged
    public void move(int fromPos, int toPos) {
        if (!checkIndex(fromPos)) {
            SLog.d(TAG, "move: fromPos is out of range fromPos = " + fromPos);
            return;
        }
        if (!checkIndex(toPos)) {
            SLog.d(TAG, "move: toPos is out of range toPos = " + toPos);
            return;
        }
        Object fromData = mProviderDatas.get(fromPos);
        Object toData = mProviderDatas.get(toPos);
        mProviderDatas.set(toPos, fromData);
        mProviderDatas.set(fromPos, toData);
//        mAdapter.notifyItemMoved(fromPos, toPos);
//        //由于move机制需要刷新move范围内的item。
//        mAdapter.notifyItemRangeChanged(fromPos, toPos - fromPos + 1);
        mAdapter.notifyItemRangeChanged(fromPos, toPos - fromPos + 1);
    }

    private <D> int findIndexInArray(D data) {
        for (int i = 0, len = mProviderDatas.size(); i < len; i++) {
            if (mProviderDatas.get(i).equals(data)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        int size = mProviderDatas.size();
        if (!mIndexWithCursorMap.isEmpty()) {
            for (HashMap.Entry<Integer, Cursor> entry : mIndexWithCursorMap.entrySet()) {
                size = size + entry.getValue().getCount() - 1;
            }
        }
        return size;
    }

    private void resetIndexWithCursorMap() {
        if (mIndexWithCursorMap.isEmpty()) {
            return;
        }
        mIndexWithCursorMap.clear();
        Object obj;
        for (int i = 0, size = mProviderDatas.size(); i < size; i++) {
            obj = mProviderDatas.get(i);
            if (obj instanceof Cursor) {
                mIndexWithCursorMap.put(i, (Cursor) obj);
            }
        }
    }

    public Object get(int position) {
        if (!mIndexWithCursorMap.isEmpty()) {
            for (HashMap.Entry<Integer, Cursor> entry : mIndexWithCursorMap.entrySet()) {
                Cursor cursor = entry.getValue();
                if (position >= entry.getKey() && position < cursor.getCount() + entry.getKey()) {
                    cursor.moveToPosition(position - entry.getKey());
                    return cursor;
                }
            }
        }
        if (position >= 0 && position < size()) {
            int realPosition = position;
            if (!mIndexWithCursorMap.isEmpty()) {
                for (HashMap.Entry<Integer, Cursor> entry : mIndexWithCursorMap.entrySet()) {
                    Cursor cursor = entry.getValue();
                    if (position > entry.getKey()) {
                        realPosition = realPosition - cursor.getCount() + 1;
                    }
                }
             }
            return mProviderDatas.get(realPosition);
        }
        return null;
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index < size();
    }

    public List getData() {
        return mProviderDatas;
    }

    public void clear() {
        mProviderDatas.clear();
        mProviderDatas = null;
        for (HashMap.Entry<Integer, Cursor> entry : mIndexWithCursorMap.entrySet()) {
            if (!entry.getValue().isClosed()) {
                entry.getValue().close();
            }
        }
        mIndexWithCursorMap.clear();
        mIndexWithCursorMap = null;
    }
}
