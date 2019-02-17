package com.example.administrator.androidtest.Common.Util.Core;

import com.google.gson.Gson;

public class GsonUtil {
    public static String toJson(Object jsonObj){
        Gson gson = new Gson();
        return gson.toJson(jsonObj);
    }

    public static <T> T toObj(String json, Class<T> clz){
        Gson gson = new Gson();
        return gson.fromJson(json, clz);
    }
}
