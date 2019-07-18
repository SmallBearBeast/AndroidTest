package com.example.libbase.Util;

import android.util.SparseArray;

import java.util.*;


/**
 * 集合与数组相关工具类
 */
@SuppressWarnings("unchecked")
public final class CollectionUtil {

    private static Object[] objToArray(Object obj){
        if(obj instanceof Object[]){
            return (Object[]) obj;
        }
        return null;
    }

    /**
     * 判断数组长度是否相等，null或者0长度返回false
     */
    public static boolean isSameLength(Object[] firstArray, Object[] secondArray){
        if(isEmpty(firstArray) || isEmpty(secondArray)){
            return false;
        }
        return firstArray.length == secondArray.length;
    }

    public static <T> boolean isSameLength(Collection<T> firstCollection, Collection<T> secondCollection){
        if(isEmpty(firstCollection) || isEmpty(secondCollection)){
            return false;
        }
        return firstCollection.size() == secondCollection.size();
    }

    public static boolean isSameLength(Object firstObj, Object secondObj){
        Object[] firstArray = objToArray(firstObj);
        Object[] secondArray = objToArray(secondObj);
        return isSameLength(firstArray, secondArray);
    }

    public static <K, V> boolean isSameLength(Map<K, V> firstMap, Map<K, V> secondMap){
        if(isEmpty(firstMap) || isEmpty(secondMap)){
            return false;
        }
        return firstMap.size() == secondMap.size();
    }

    public static <T> boolean isSameLength(SparseArray<T> firstSparseArray, SparseArray<T> secondSparseArray) {
        if(isEmpty(firstSparseArray) || isEmpty(secondSparseArray)){
            return false;
        }
        return firstSparseArray.size() == secondSparseArray.size();
    }
    /**判断数组长度是否相等，null或者0长度返回false**/

    /**
     * 判空方法
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 主要用于基本类型数组
     */
    public static boolean isEmpty(Object obj){
        Object[] array = objToArray(obj);
        return array == null || array.length == 0;
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
    @SuppressWarnings("unchecked")
    public static <T> void addToListNull(List<T> list, Object obj){
        Object[] array = objToArray(obj);
        if(isEmpty(array))
            return;
        for (int i = 0, len = array.length; i < len; i++) {
            list.add((T) array[i]);
        }
    }

    public static <T> void addToListNotNull(List<T> list, Object obj){
        Object[] array = objToArray(obj);
        if(isEmpty(array))
            return;
        for (int i = 0, len = array.length; i < len; i++) {
            if(array[i] != null){
                list.add((T) array[i]);
            }
        }
    }

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
    public static <T> List<T> asListNull(Object obj){
        Object[] array = objToArray(obj);
        if(array == null)
            return null;
        List<T> list = new ArrayList<>();
        for (int i = 0, len = array.length; i < len; i++) {
            list.add((T) array[i]);
        }
        return list;
    }


    public static <T> List<T> asListNotNull(Object obj){
        Object[] array = objToArray(obj);
        if(array == null)
            return null;
        List<T> list = new ArrayList<>();
        for (int i = 0, len = array.length; i < len; i++) {
            if(array[i] != null) {
                list.add((T) array[i]);
            }
        }
        return list;
    }

    public static <T> List<T> asListNull(T... array){
        List<T> list = new ArrayList<>();
        for (int i = 0, len = array.length; i < len; i++) {
            list.add(array[i]);
        }
        return list;
    }

    public static <T> List<T> asListNotNull(T... array){
        List<T> list = new ArrayList<>();
        for (int i = 0, len = array.length; i < len; i++) {
            if(array[i] != null) {
                list.add(array[i]);
            }
        }
        return list;
    }
    /**数组类型转list**/

    /**
     * 获取数组第一个元素
     */

    public static <T> T getFirst(Object obj){
        Object[] array = objToArray(obj);
        if(isEmpty(array))
            return null;
        return (T) array[0];
    }

    public static <T> T getFirst(Collection<T> collection){
        Iterator<T> it = collection.iterator();
        if(it.hasNext()){
            return it.next();
        }
        return null;
    }
    /**获取数组第一个元素**/

    /**
     * 通过keys和vals数组生成map
     */

    public static <K, V> Map<K, V> asMap(Object keys, Object vals){
        Object[] keyArrays = objToArray(keys);
        Object[] valArrays = objToArray(vals);
        if(keyArrays != null && valArrays != null && keyArrays.length == valArrays.length){
            Map<K, V> map = new HashMap<>();
            for (int i = 0, len = keyArrays.length; i < len; i++) {
                map.put((K)keyArrays[i], (V)valArrays[i]);
            }
            return map;
        }
        return null;
    }

    public static <K, V> Map<K, V> asMap(List<K> keys, List<V> vals){
        if(keys != null && vals != null && keys.size() == vals.size()){
            Map<K, V> map = new HashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                map.put(keys.get(i), vals.get(i));
            }
            return map;
        }
        return null;
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
