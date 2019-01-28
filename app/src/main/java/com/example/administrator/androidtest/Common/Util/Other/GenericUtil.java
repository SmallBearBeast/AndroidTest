package com.example.administrator.androidtest.Common.Util;

import java.util.ArrayList;
import java.util.List;

public class GenericUtil {

    public static <T> T toT(Object obj, Class<T> clz){
        if(obj.getClass().isAssignableFrom(clz)){
            return (T) obj;
        }
        return null;
    }


    public static <T> List<T> toListT(List list, Class<T> clz){
        List<T> tList = new ArrayList<>();
        for (Object obj : list) {
            tList.add(toT(obj, clz));
        }
        return tList;
    }

}
