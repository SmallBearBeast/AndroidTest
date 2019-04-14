package com.example.administrator.androidtest.Common.Util.Core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonUtil {
    public static String toJson(Object jsonObj){
        Gson gson = new Gson();
        return gson.toJson(jsonObj);
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObj(String json, Class<T> clz){
        if(clz == String.class)
            return (T) json;
        Gson gson = new Gson();
        return gson.fromJson(json, clz);
    }

    public static <T> List<T> toList(String json, TypeToken<List<T>> token){
        Gson gson = new Gson();
        return gson.fromJson(json, token.getType());
    }
}
