package com.example.readandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static void main (String[] args) {
        List<String> list = bindOther("1234");
        String s = null;
    }

    public static List<String> bindOther(String s) {
        List<String> list = new ArrayList<>();
        char[] nums = s.toCharArray();
        int temp;
        int index;
        char swap;
        while (true) {
            temp = nums.length - 1;
            while (temp >= 0 && nums[nums.length - 1] <= nums[temp]) {
                temp --;
            }
            if (temp < 0) {
                return list;
            }
            swap = nums[nums.length - 1];
            for (int i = nums.length - 1; i > temp; i--) {
                nums[i] = nums[i - 1];
            }
            nums[temp] = swap;
            list.add(new String(nums));
        }
    }
}
