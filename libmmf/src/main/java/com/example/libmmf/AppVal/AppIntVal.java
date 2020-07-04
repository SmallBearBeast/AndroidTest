package com.example.libmmf.AppVal;


public class AppIntVal extends AppVal {
    private int mVal;

    public AppIntVal(String key, int val) {
        super(key);
        mVal = val;
    }

    public AppIntVal(String spName, String key, int val) {
        super(spName, key);
        mVal = val;
    }

    public int get() {
        mVal = getSp().getInt(getKey(), mVal);
        return mVal;
    }

    public void set(int val) {
        mVal = val;
        getEditor().putInt(getKey(), mVal).apply();
    }

    public void inc() {
        set(mVal + 1);
    }

    public void dec() {
        set(mVal - 1);
    }

    public void plus(int num) {
        set(mVal + num);
    }

    public void minus(int num) {
        set(mVal - num);
    }

    public void reset() {
        set(0);
    }
}
