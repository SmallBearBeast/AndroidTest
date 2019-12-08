package com.example.administrator.androidtest.Common.Media.Info;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DirInfo {
    public String mDirName;
    public String mDirPath;
    public long mCount;
    public List<BaseInfo> mBaseInfos;

    public DirInfo(){}

    public DirInfo(String dirPath){
        mDirPath = dirPath;
        mDirName = dirPath.substring(dirPath.lastIndexOf("/") + 1);
        mBaseInfos = new ArrayList<>();
    }

    public void addBaseInfo(BaseInfo baseInfo) {
        if (mBaseInfos == null) {
            mBaseInfos = new ArrayList<>();
        }
        mBaseInfos.add(baseInfo);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirInfo dirInfo = (DirInfo) o;

        return Objects.equals(mDirPath, dirInfo.mDirPath);
    }

    @Override
    public int hashCode() {
        return mDirPath != null ? mDirPath.hashCode() : 0;
    }
}
