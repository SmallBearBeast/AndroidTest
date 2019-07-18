package com.example.libbase.Util;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class UriUtil {
    public static boolean isContentUri(Uri uri){
        if(uri != null){
            return uri.getPath().startsWith("content:");
        }
        return false;
    }

    public static boolean isFileUri(Uri uri){
        if(uri != null){
            return uri.getPath().startsWith("file:");
        }
        return false;
    }

    /**通过uri获取路径，大小，Mime格式等等**/
    public static String uriToPath(Uri uri){
        MediaInfo mediaInfo = uriToMediaInfo(uri);
        return mediaInfo.mPath;
    }

    public static long uriToLength(Uri uri){
        MediaInfo mediaInfo = uriToMediaInfo(uri);
        return mediaInfo.mSize;
    }

    public static String uriToMime(Uri uri){
        MediaInfo mediaInfo = uriToMediaInfo(uri);
        return mediaInfo.mMimeType;
    }

    public static MediaInfo uriToMediaInfo(Uri uri){
        MediaInfo mediaInfo = new MediaInfo();
        Cursor cursor = AppUtil.getApp().getContentResolver().query(uri, null, null, null, null);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                mediaInfo.mPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                mediaInfo.mAddDate = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED));
                mediaInfo.mModifyDate = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED));
                mediaInfo.mMimeType = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
                mediaInfo.mSize = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
            }
            cursor.close();
        }
        return mediaInfo;
    }
    /**通过uri获取路径，大小，Mime格式等等**/


    /**
     * 文件路径转对应的uri
     */
    public static Uri filePathToUri(String path){
        return Uri.parse("file:///" + path);
    }
    /**文件路径转对应的uri**/

    public static class MediaInfo{
        String mPath;
        String mAddDate;
        String mModifyDate;
        String mMimeType;
        long mSize;
    }
}
