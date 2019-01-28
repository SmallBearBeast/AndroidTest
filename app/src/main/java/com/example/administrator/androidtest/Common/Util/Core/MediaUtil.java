package com.example.administrator.androidtest.Common.Util.Core;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.administrator.androidtest.Common.Util.AppInitUtil;

public class MediaUtil extends AppInitUtil {
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
        Cursor cursor = sContext.getContentResolver().query(uri, null, null, null, null);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                mediaInfo.mPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                mediaInfo.mAddDate = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED));
                mediaInfo.mModifyDate = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED));
                mediaInfo.mMimeType = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
                mediaInfo.mSize = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
            }
        }
        return mediaInfo;
    }

    public static class MediaInfo{
        String mPath;
        String mAddDate;
        String mModifyDate;
        String mMimeType;
        long mSize;
    }
}
