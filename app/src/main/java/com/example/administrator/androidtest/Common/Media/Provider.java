package com.example.administrator.androidtest.Common.Media;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class Provider {


    protected static final int RECENT_COUNT = 100;
    protected static final int OTHER_COUNT = 50;
    protected static final int LIMIT_COUNT = 10;
    protected static final String DCIM = "DCIM"; //相册
    protected static final String CAMERA = "Camera"; //相册
    protected static final String OTHER = "OTHER"; //其他相册
    protected static final String PATH = "NONE"; //路径不存在
    protected static final String DESC = MediaStore.Images.Media.DATE_TAKEN + " DESC";

    protected static final long IMAGE_MIN_SIZE = 1000L;
    protected static final String[] IMAGE_MIMES = new String[]{
            "image/jpg", "image/jpeg", "image/png" , "image/wbep"
    };
    protected static final long VIDEO_MIN_SIZE = 1000L;
    protected static final String[] VIDEO_MIMES = new String[]{
            "video/mp4"
    };

    protected boolean mIsLoad;
    protected DirInfo mRecentDir;
    protected DirInfo mDcimDir;
    protected Context mContext;
    protected DataCallback mDataCallback;
    protected List<DirInfo> mHandleList = new ArrayList<>();
    protected List<DirInfo> mDirInfoList = new ArrayList<>();

    protected DirInfo getDirInfoByPath(String path){
        for (DirInfo info : mDirInfoList) {
            if(info.mDirPath.equals(path)){
                return info;
            }
        }
        return null;
    }

    public boolean isDirEmpty(DirInfo info) {
        if(info == null || info.mCount == 0)
            return true;
        return false;
    }

    public interface DataCallback{
        void onData(List<DirInfo> infos);
    }

    public static class DirInfo{
        public String mDirName;
        public String mDirPath;
        public int mCount;
        public long mSize;
        public List<MediaInfo> mMediaInfos = new ArrayList<>();

        public DirInfo(){}

        public DirInfo(String dirName, String dirPath){
            mDirName = dirName;
            mDirPath = dirPath;
        }

        public void addMediaInfo(MediaInfo mediaInfo){
            mMediaInfos.add(mediaInfo);
        }

        public void addMediaInfo(DirInfo albumInfo){
            if(albumInfo != null){
                mCount += albumInfo.mCount;
                mSize += albumInfo.mSize;
                mMediaInfos.addAll(albumInfo.mMediaInfos);
            }
        }
    }

    public static class MediaInfo{
        public String mPath;
        public String mName;
        public String mMime;
        public long mSize;
        public long mAddDate;
        public int mWidth;
        public int mHeight;
    }

    protected void cursorToMedia(Cursor cursor, MediaInfo info) {
        if(info != null){
            info.mPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            info.mName = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
            info.mMime = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
            info.mSize = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
            info.mAddDate = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED));
            info.mWidth = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.WIDTH));
            info.mHeight = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.HEIGHT));
        }
    }

}
