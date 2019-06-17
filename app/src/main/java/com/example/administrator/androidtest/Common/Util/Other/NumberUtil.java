package com.example.administrator.androidtest.Common.Util.Other;

import com.facebook.common.internal.Preconditions;

public class NumberUtil {

    public static int max(int ... array) {
        Preconditions.checkArgument(array.length > 0);
        int max = array[0];

        for(int i = 1; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    public static int min(int ... array) {
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
