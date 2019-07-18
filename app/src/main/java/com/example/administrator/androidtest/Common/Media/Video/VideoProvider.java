package com.example.administrator.androidtest.Common.Media.Video;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.administrator.androidtest.Common.Media.Provider;
import com.example.libbase.Util.FileUtil;
import com.example.libbase.Util.MainThreadUtil;
import com.example.libbase.Util.ThreadUtil;

import java.util.ArrayList;

public class VideoProvider extends Provider {

    public VideoProvider(Context context){
        mContext = context;
    }


    public void fetchAlbum(DataCallback callback){
        fetchAlbum(VIDEO_MIN_SIZE, VIDEO_MIMES, callback);
    }

    /**
     * 获取相册包括最近图片相册，本地相册，其他内部图片满足条件的相册（比如大小>200kb）
     */
    public void fetchAlbum(final long size, final String[] mimes, DataCallback callback){
        if(mIsLoad)
            return;
        mDataCallback = callback;
        mIsLoad = true;

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                StringBuilder builder = new StringBuilder();
                int length = (mimes == null ? 0 : mimes.length);
                for (int i = 0; i < length; i++) {
                    if(i == mimes.length - 1){
                        builder.append(MediaStore.Images.Media.MIME_TYPE + "=?");
                    }else {
                        builder.append(MediaStore.Images.Media.MIME_TYPE + "=? or ");
                    }
                }
                if(length == 0){
                    builder.append(MediaStore.Images.Media.SIZE + ">?");
                }else {
                    builder.append(" and ").append(MediaStore.Video.Media.SIZE + ">?");
                }
                String selection = builder.toString();
                String[] selectionArgs = new String[length + 1];
                if(mimes != null) {
                    System.arraycopy(mimes, 0, selectionArgs, 0, length);
                    selectionArgs[length] = String.valueOf(size);
                }
                Cursor cursor = mContext.getContentResolver().query(uri, null, selection, selectionArgs, DESC);
                mDirInfoList.clear();

                mRecentDir = new DirInfo();
                int recentCount = RECENT_COUNT;
                if(cursor != null){
                    while (cursor.moveToNext()){
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        String dirPath = FileUtil.getParent(path);
                        //过滤根目录
                        if(dirPath == null){
                            continue;
                        }
                        DirInfo dirInfo = getDirInfoByPath(dirPath);
                        VideoInfo videoInfo = cursorToVideo(cursor);
                        if(dirInfo == null){
                            dirInfo = new DirInfo();
                            mDirInfoList.add(dirInfo);
                            dirInfo.mDirPath = dirPath;
                            dirInfo.mDirName = FileUtil.getName(dirPath);
                            if(dirInfo.mDirName.equals(DCIM)){
                                mDcimDir = dirInfo;
                            }
                        }
                        if(recentCount > 0) {
                            mRecentDir.mCount ++;
                            mRecentDir.mSize += videoInfo.mSize;
                            mRecentDir.addMediaInfo(videoInfo);
                            recentCount --;
                        }
                        dirInfo.mCount ++;
                        dirInfo.mSize += videoInfo.mSize;
                        dirInfo.addMediaInfo(videoInfo);
                    }
                }


                handleAlbum();
            }
        });
    }

    /**
     * 对相册进行处理和整合排序
     */
    private void handleAlbum() {
        mHandleList = new ArrayList<>();
        DirInfo otherAlbum = new DirInfo(OTHER, PATH);
        for (DirInfo info : mDirInfoList) {
            if(info.mDirName.equals(DCIM)){
                continue;
            }
            if(info.mSize > LIMIT_COUNT){
                mHandleList.add(info);
            }else {
                if(otherAlbum.mCount < OTHER_COUNT && !info.mDirName.equals(DCIM)) {
                    otherAlbum.addMediaInfo(info);
                }
            }
        }
        mHandleList.add(0, mDcimDir);
        mHandleList.add(0, mRecentDir);
        mHandleList.add(otherAlbum);

        if(mDataCallback != null){
            MainThreadUtil.run(new Runnable() {
                @Override
                public void run() {
                    mDataCallback.onData(mHandleList);
                    mIsLoad = false;
                }
            });
        }
    }

    private VideoInfo cursorToVideo(Cursor cursor) {
        VideoInfo info = new VideoInfo();
        info.mDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
        return info;
    }
    
    public static class VideoInfo extends MediaInfo{
        public long mDuration;
    }

}
