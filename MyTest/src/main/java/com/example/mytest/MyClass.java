package com.example.mytest;

public class MyClass {
    public static void main(String[] args){
        int[] nums = new int[]{
                5, 4, 3, 2 , 1
        };
        System.out.println(min(nums));
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
}
