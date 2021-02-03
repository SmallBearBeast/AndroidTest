package com.example.administrator.androidtest.Test.KVCompareTest;

public class ReflectData {
    private String privateReflectDataName1;
    public String privateReflectDataName2;

    public ReflectData(String privateReflectDataName1, String privateReflectDataName2) {
        this.privateReflectDataName1 = privateReflectDataName1;
        this.privateReflectDataName2 = privateReflectDataName2;
    }

    private String getName1(){
        return privateReflectDataName1;
    }

    public String getName2(){
        return privateReflectDataName2;
    }
}
