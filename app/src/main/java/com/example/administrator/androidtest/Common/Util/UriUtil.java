package com.example.administrator.androidtest.Common.Util;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.administrator.androidtest.App;

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

    public static Uri pathToUri(String path){
        return null;
    }

    public static String uriToPath(Uri uri){
        String path = null;
        Cursor cursor = App.getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            cursor.close();
        }
        return path;
    }

    public static long uriToLength(Uri uri){
        long size = 0L;
        Cursor cursor = App.getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
            }

            cursor.close();
        }
        return size;
    }
}
