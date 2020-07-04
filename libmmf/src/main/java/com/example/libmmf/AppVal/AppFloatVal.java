package com.example.libmmf.AppVal;

public class AppFloatVal extends AppVal {
    private float mVal;

    public AppFloatVal(String key, float val) {
        super(key);
        mVal = val;
    }

    public AppFloatVal(String spName, String key, float val) {
        super(spName, key);
        mVal = val;
    }

    public float get() {
        mVal = getSp().getFloat(getKey(), mVal);
        return mVal;
    }

    public void set(float val) {
        mVal = val;
        getEditor().putFloat(getKey(), mVal).apply();
    }

    public void reset() {
        set(0f);
    }
}
