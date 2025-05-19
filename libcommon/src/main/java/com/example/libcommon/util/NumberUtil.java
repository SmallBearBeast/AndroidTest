package com.example.libcommon.util;


import java.util.List;

public class NumberUtil {

    public static int max(int ... array) {
        int max = array[0];
        for(int i = 1; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static int min(int ... array) {
        int min = array[0];
        for(int i = 1; i < array.length; ++i) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static <T extends Comparable> T max(List<T> array){
        T max = array.get(0);
        for (int i = 1, size = array.size(); i < size; i++) {
            if(array.get(i).compareTo(max) > 0){
                max = array.get(i);
            }
        }
        return max;
    }


    public static <T extends Comparable> T min(List<T> array){
        T min = array.get(0);
        for (int i = 1, size = array.size(); i < size; i++) {
            if(array.get(i).compareTo(min) < 0){
                min = array.get(i);
            }
        }
        return min;
    }
}
