package com.example.administrator.androidtest.Common.Util.Other;

import com.android.internal.util.Preconditions;

import java.util.List;

@SuppressWarnings("unchecked")
public class OtherUtil {
    public static <T extends Comparable> T maxList(List<T> array){
        Preconditions.checkArgument(array.size() > 0);
        T max = array.get(0);
        for (int i = 1, size = array.size(); i < size; i++) {
            if(array.get(i).compareTo(max) > 0){
                max = array.get(i);
            }
        }
        return max;
    }


    public static <T extends Comparable> T minList(List<T> array){
        Preconditions.checkArgument(array.size() > 0);
        T min = array.get(0);
        for (int i = 1, size = array.size(); i < size; i++) {
            if(array.get(i).compareTo(min) < 0){
                min = array.get(i);
            }
        }
        return min;
    }

    public static int maxArray(int ... array) {
        Preconditions.checkArgument(array.length > 0);
        int max = array[0];
        for(int i = 1; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static int minArray(int ... array) {
        Preconditions.checkArgument(array.length > 0);
        int min = array[0];
        for(int i = 1; i < array.length; ++i) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
}
