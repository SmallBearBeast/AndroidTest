package com.example.administrator.androidtest.Common.Media.Image;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.administrator.androidtest.Common.Media.Provider;
import com.example.administrator.androidtest.Common.Util.File.FileUtil;
import com.example.administrator.androidtest.Common.Util.Core.ThreadUtil;
import com.example.administrator.androidtest.Common.Util.Core.MainThreadUtil;

import java.util.ArrayList;

public class ImageProvider extends Provider {

    public ImageProvider(Context context){
        mContext = context;
    }

    public void fetchAlbum(DataCallback callback){
        fetchAlbum(IMAGE_MIN_SIZE, IMAGE_MIMES, callback);
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
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
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
                    builder.append(" and ").append(MediaStore.Images.Media.SIZE + ">?");
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
                        ImageInfo imageInfo = cursorToImage(cursor);
                        if(dirInfo == null){
                            dirInfo = new DirInfo();
                            dirInfo.mDirPath = dirPath;
                            dirInfo.mDirName = FileUtil.getName(dirPath);
                            if(dirInfo.mDirName.equals(DCIM) || dirInfo.mDirName.equals(CAMERA)){
                                mDcimDir = dirInfo;
                            }
                            mDirInfoList.add(dirInfo);
                        }
                        if(recentCount > 0) {
                            mRecentDir.mCount ++;
                            mRecentDir.mSize += imageInfo.mSize;
                            mRecentDir.addMediaInfo(imageInfo);
                            recentCount --;
                        }
                        dirInfo.mCount ++;
                        dirInfo.mSize += imageInfo.mSize;
                        dirInfo.addMediaInfo(imageInfo);
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
        DirInfo otherDir = new DirInfo(OTHER, PATH);
        for (DirInfo info : mDirInfoList) {
            if(info.mDirName.equals(DCIM) || info.mDirName.equals(CAMERA)){
                continue;
            }
            if(info.mCount > LIMIT_COUNT){
                mHandleList.add(info);
            }else {
                if(otherDir.mCount < OTHER_COUNT && !info.mDirName.equals(DCIM) && !info.mDirName.equals(CAMERA)) {
                    otherDir.addMediaInfo(info);
                }
            }
        }
        if(!isDirEmpty(mDcimDir)){
            mHandleList.add(0, mDcimDir);
        }
        if(!isDirEmpty(mRecentDir)){
            mHandleList.add(0, mRecentDir);
        }
        if(!isDirEmpty(otherDir)) {
            mHandleList.add(otherDir);
        }

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

    private ImageInfo cursorToImage(Cursor cursor) {
        ImageInfo info = new ImageInfo();
        info.mOrientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION));
        cursorToMedia(cursor, info);
        return info;
    }
    
    public static class ImageInfo extends MediaInfo{
        public int mOrientation;
    }

}
