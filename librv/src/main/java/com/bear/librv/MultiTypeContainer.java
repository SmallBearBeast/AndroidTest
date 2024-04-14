package com.bear.librv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiTypeContainer {
    private List<Type<?>> typeList;

    public MultiTypeContainer() {
        typeList = new ArrayList<>();
    }

    public int size() {
        return typeList.size();
    }

    public void register(Type<?> type) {
        typeList.add(type);
    }

    public void unRegister(Type<?> type) {
        Iterator<Type<?>> iterator = typeList.iterator();
        while (iterator.hasNext()) {
            Type<?> curType = iterator.next();
            if (curType.getClazz() == type.getClazz()) {
                iterator.remove();
            }
        }
    }

    public boolean contain(Type<?> type) {
        for (Type<?> curType : typeList) {
            if (curType.getClazz() == type.getClazz()) {
                return true;
            }
        }
        return false;
    }

    public boolean contain(Class<?> clazz) {
        for (Type<?> curType : typeList) {
            if (curType.getClazz() == clazz) {
                return true;
            }
        }
        return false;
    }

    public int getItemViewType(Object obj, int position) {
        Class<?> clz = obj.getClass();
        for (int i = 0; i < typeList.size(); i++) {
            Type<Object> type = (Type<Object>) typeList.get(i);
            if (type.getClazz() == clz) {
                return i + type.getLinker().index(obj, position);
            }
        }
        for (int i = 0; i < typeList.size(); i++) {
            Type<Object> type = (Type<Object>) typeList.get(i);
            if (typeList.get(i).getClazz().isAssignableFrom(clz)) {
                return i + type.getLinker().index(obj, position);
            }
        }
        return -1;
    }

    public void clear() {
        typeList.clear();
        typeList = null;
    }

    public <T> Type<T> getType(int index) {
        return (Type<T>) typeList.get(index);
    }

    public List<Type<?>> getTypeList() {
        return typeList;
    }

    public List<MultiTypeDelegate<?, ?>> getDelegateList() {
        List<MultiTypeDelegate<?, ?>> delegateList = new ArrayList<>();
        for (Type<?> type : typeList) {
            delegateList.add(type.getDelegate());
        }
        return delegateList;
    }
}
