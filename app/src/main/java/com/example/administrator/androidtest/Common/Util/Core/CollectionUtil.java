package com.example.administrator.androidtest.Common.Util;

import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 集合与数组相关工具类
 */

public final class CollectionUtil {

    /**
     * 判空方法
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isEmpty(T[] list) {
        return list == null || list.length == 0;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(SparseArray<T> sparseArray) {
        return sparseArray == null || sparseArray.size() == 0;
    }
    /**判空方法**/

    /**
     * 添加元素到list，有两种：是否允许伟null
     * 不能使用Arrays.asList() 范型类型会是数组类型
     */
    public static <T> void addToListNull(List<T> list, T... datas){
        for (int i = 0; i < datas.length; i++) {
            list.add(datas[i]);
        }
    }

    public static <T> void addToListNotNull(List<T> list, T... datas){
        for (int i = 0; i < datas.length; i++) {
            if(datas[i] != null) {
                list.add(datas[i]);
            }
        }
    }
    /**添加元素到list，有两种：是否允许伟null**/

    /**
     * 数组类型转list
     */
    public static <T> List<T> arrayToListNull(T... datas){
        List<T> list = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            list.add(datas[i]);
        }
        return list;
    }

    public static <T> List<T> arrayToListNotNull(T... datas){
        List<T> list = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            if(datas[i] != null) {
                list.add(datas[i]);
            }
        }
        return list;
    }
    /**数组类型转list**/

    /**
     * 通过keys和vals数组生成map
     */
    public static <K, V> Map<K, V> asMap(K[] keys, V[] vals){
        return asMap(Arrays.asList(keys), Arrays.asList(vals));
    }

    public static <K, V> Map<K, V> asMap(List<K> keys, List<V> vals){
        Map<K, V> map = new HashMap<>();
        if(keys != null && vals != null && keys.size() == vals.size()){
            for (int i = 0; i < keys.size(); i++) {
                map.put(keys.get(i), vals.get(i));
            }
        }
        return map;
    }
    /**通过keys和vals数组生成map**/

    /**
     * 过滤list中重复数据
     */
    public static <T> List<T> filterList(List<T> list) {
        if (isEmpty(list)) {
            return list;
        }
        Set<T> set = new HashSet<>();
        List<T> newList = new ArrayList<>();
        for (T element : list) {
            if (set.add(element)) {
                newList.add(element);
            }
        }
        set.clear();
        list.clear();
        return newList;
    }
    /**过滤list中重复数据**/
}
