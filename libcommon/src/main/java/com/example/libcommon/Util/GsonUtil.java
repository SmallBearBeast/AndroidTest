package com.example.libcommon.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

@SuppressWarnings("unchecked")
public class GsonUtil {
    public static String toJson(Object jsonObj){
        return new GsonBuilder().serializeNulls().create().toJson(jsonObj);
    }

    static <T> T toObj(String json, TypeToken<T> token){
        if(token.getType() == String.class) {
            return (T) json;
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(json, token.getType());
    }
}
