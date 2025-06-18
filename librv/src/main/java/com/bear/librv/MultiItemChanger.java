package com.bear.librv;

import android.annotation.SuppressLint;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to handle specific add, remove, and update operation.
 * Note: Cursor is read-only.
 */
public class MultiItemChanger {
    private static final String TAG = RvLog.RV_LOG_TAG;

    private List<Object> internalItemList = new ArrayList<>();
    private Map<Integer, Cursor> mIndexWithCursorMap = new HashMap<>();
    private MultiTypeAdapter multiTypeAdapter;

    public void attachAdapter(MultiTypeAdapter adapter) {
        multiTypeAdapter = adapter;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<?> itemList) {
        if (itemList == null) {
            RvLog.w(TAG, "setItems: itemList is null");
            return;
        }
        List<Object> regItemList = new ArrayList<>();
        for (Object item : itemList) {
            if (!multiTypeAdapter.isRegister(item)) {
                RvLog.w(TAG, "setItems: " + item.getClass().getSimpleName() + " is not registered");
            } else {
                regItemList.add(item);
            }
        }
        internalItemList.clear();
        internalItemList.addAll(regItemList);
        multiTypeAdapter.notifyDataSetChanged();
    }

    public void addCursor(int index, Cursor cursor) {
        if (cursor == null) {
            RvLog.w(TAG, "addCursor: cursor is null");
            return;
        }
        if (cursor.getCount() == 0) {
            RvLog.w(TAG, "addCursor: cursor count is 0");
            return;
        }
        if (!multiTypeAdapter.isRegister(cursor)) {
            RvLog.w(TAG, "addCursor: cursor is not registered");
            return;
        }
        if (!checkIndex(index)) {
            RvLog.w(TAG, "addCursor: index is out of range");
            return;
        }
        internalItemList.add(index, cursor);
        mIndexWithCursorMap.put(index, cursor);
        multiTypeAdapter.notifyItemRangeInserted(index, cursor.getCount());
    }

    public void addCursorFirst(Cursor cursor) {
        addCursor(0, cursor);
    }

    public void addCursorLast(Cursor cursor) {
        addCursor(size(), cursor);
    }


    public void addFirst(Object data) {
        addAll(0, data);
    }

    public void addFirst(List<Object> itemList) {
        addAll(0, itemList);
    }

    public void addLast(Object data) {
        addAll(size(), data);
    }

    public void addLast(List<Object> itemList) {
        addAll(size(), itemList);
    }

    public void addAll(int index, Object... dataArray) {
        addAll(index, Arrays.asList(dataArray));
    }

    public void addAll(int index, List<Object> itemList) {
        if (!checkIndex(index)) {
            RvLog.w(TAG, "addAll: index is out of range");
            return;
        }
        if (itemList.isEmpty()) {
            RvLog.w(TAG, "addAll: itemList is empty");
            return;
        }
        List<Object> regItemList = new ArrayList<>();
        for (Object item : itemList) {
            if (!multiTypeAdapter.isRegister(item)) {
                RvLog.w(TAG, "add: " + item.getClass().getSimpleName() + " is not registered");
            } else {
                regItemList.add(item);
            }
        }
        internalItemList.addAll(index, regItemList);
        multiTypeAdapter.notifyItemRangeInserted(index, regItemList.size());
    }

    // TODO: 2019-10-20 need removeCursor and updateCursor method
    public void remove(Object... itemArray) {
        for (Object obj : itemArray) {
            remove(findIndexInArray(obj), 1);
        }
    }

    public void remove(List<Object> itemList) {
        if (!itemList.isEmpty()) {
            for (Object item : itemList) {
                remove(findIndexInArray(item), 1);
            }
        }
    }

    public void remove(int index, int num) {
        if (num > 0 && index >= 0 && index + num <= size()) {
            internalItemList.subList(index, num + index).clear();
            multiTypeAdapter.notifyItemRangeRemoved(index, num);
        }
        resetIndexWithCursorMap();
    }

    public void remove(int index) {
        remove(index, 1);
    }

    public void removeFirst(int num) {
        remove(0, num);
    }

    public void removeLast(int num) {
        remove(internalItemList.size() - num, num);
    }

    public void update(int index, Object obj, Payload payload) {
        if (!checkIndex(index)) {
            RvLog.w(TAG, "update: index is out of range");
            return;
        }
        if (obj != null) {
            internalItemList.set(index, obj);
        }
        multiTypeAdapter.notifyItemChanged(index, payload);
    }

    public void update(Object obj) {
        if (obj != null) {
            update(findIndexInArray(obj), obj, null);
        }
    }

    public void update(Object obj, Payload payload) {
        if (obj != null) {
            update(findIndexInArray(obj), obj, payload);
        }
    }

    public void update(int index, Object obj) {
        update(index, obj, null);
    }

    public void update(int index) {
        update(index, null, null);
    }

    public void move(int fromPos, int toPos) {
        if (!checkIndex(fromPos)) {
            RvLog.w(TAG, "move: fromPos is out of range fromPos = " + fromPos);
            return;
        }
        if (!checkIndex(toPos)) {
            RvLog.w(TAG, "move: toPos is out of range toPos = " + toPos);
            return;
        }
        Object fromData = internalItemList.get(fromPos);
        Object toData = internalItemList.get(toPos);
        internalItemList.set(toPos, fromData);
        internalItemList.set(fromPos, toData);
        multiTypeAdapter.notifyItemMoved(fromPos, toPos);
//        //由于move机制需要刷新move范围内的item。
//        multiTypeAdapter.notifyItemRangeChanged(fromPos, toPos - fromPos + 1);
        multiTypeAdapter.notifyItemRangeChanged(fromPos, toPos - fromPos + 1);
    }

    private int findIndexInArray(Object item) {
        for (int i = 0, len = internalItemList.size(); i < len; i++) {
            if (internalItemList.get(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        int size = internalItemList.size();
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
        for (int i = 0, size = internalItemList.size(); i < size; i++) {
            obj = internalItemList.get(i);
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
            return internalItemList.get(realPosition);
        }
        return null;
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index <= size();
    }

    public List<Object> getData() {
        return internalItemList;
    }

    public void clear() {
        internalItemList.clear();
        internalItemList = null;
        for (HashMap.Entry<Integer, Cursor> entry : mIndexWithCursorMap.entrySet()) {
            if (!entry.getValue().isClosed()) {
                entry.getValue().close();
            }
        }
        mIndexWithCursorMap.clear();
        mIndexWithCursorMap = null;
    }
}
