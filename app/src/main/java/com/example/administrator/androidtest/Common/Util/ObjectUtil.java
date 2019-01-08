package com.example.administrator.androidtest.Common.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectUtil{

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
}
