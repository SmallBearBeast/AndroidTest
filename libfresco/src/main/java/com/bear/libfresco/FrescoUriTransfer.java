package com.bear.libfresco;

import android.net.Uri;
import com.bear.libcommon.util.FileUtil;

import java.io.File;

public class FrescoUriTransfer {
    public static Uri fileUri(String path){
        if(FileUtil.isFileExist(path)){
            return Uri.fromFile(new File(path));
        }
        return null;
    }

    public static Uri fileUri(File file){
        if(FileUtil.isFileExist(file)){
            return Uri.fromFile(file);
        }
        return null;
    }

    public static Uri idUri(int id){
        return Uri.parse("res:///" + id);
    }

    public static Uri urlUri(String url){
        return Uri.parse(url);
    }
}
