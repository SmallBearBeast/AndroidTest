package com.example.liblog;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public abstract class RLog<T extends RLog> {
    private final String TAG = getClass().getSimpleName();
    private static final Map<Class, RLog> mReporterMaps = new HashMap<>();
    private Map<String, Object> mParam = new ConcurrentHashMap<>();

    public static <T> T get(Class<T> clazz){
        RLog RLog = mReporterMaps.get(clazz);
        if(RLog == null){
            try {
                RLog = (RLog) clazz.newInstance();
                mReporterMaps.put(clazz, RLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) RLog;
    }

    public T of(String key, Object value) {
        Map<String, Object> map = mParam;
        if (map != null) {
            try {
                map.put(key, String.valueOf(value));
            } catch (Exception e) {
                SLog.i(TAG, key + " with error type " + value);
            }
        }
        return (T) this;
    }

    public T of(Map<String, String> values) {
        Map<String, Object> map = mParam;
        if (map != null && values != null) {
            try {
                map.putAll(values);
            } catch (Exception e) {
                SLog.i(TAG, "withAll error");
            }
        }
        return (T) this;
    }

    public boolean exist(String key){
        return mParam.containsKey(key);
    }

    public String get(String key) {
        if(exist(key)){
            return String.valueOf(mParam.get(key));
        }
        return null;
    }

    public void report() {
        Map<String, Object> map = mParam;
        if (map != null) {
            SLog.i(TAG, "report: " + map);
//            IMO.monitor.logJack(getNameSpace(nameSpace()), map);
        }
        remove();
    }

    public void remove() {
        Map<String, Object> map = mParam;
        if (map != null) {
            map.clear();
        }
    }

//    private String getNameSpace(String nameSpace) {
//        return Constants.IS_STABLE ? nameSpace + "_stable" : nameSpace + "_beta";
//    }

    protected abstract String nameSpace();
}
