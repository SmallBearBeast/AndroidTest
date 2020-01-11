package com.example.administrator.androidtest.Test.ViewModelTest;

public class ViewModelData {
    public String mName;
    public int mAge;

    public ViewModelData(String name, int age) {
        mName = name;
        mAge = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "mName='" + mName + '\'' +
                ", mAge=" + mAge +
                '}';
    }
}
