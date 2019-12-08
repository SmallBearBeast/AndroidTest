package com.example.libbase.Util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil extends AppInitUtil{
    private static final int Permission_Request_Code = 1;

    /**
     * 权限申请
     */
    public static boolean requestPermissions(String[] permissions){
        Activity activity = null;
        if(getContext() instanceof Activity){
            activity = (Activity) getContext();
        }
        if(activity == null){
            return false;
        }

        List<String> needToAsk = new ArrayList<>();
        for (String s : permissions) {
            if(!isCheckPermission(s, activity)){
                needToAsk.add(s);
            }else {
                if(isIgnorePermisson(s, activity)){
                    needToAsk.add(s);
                }
            }
        }
        if(!needToAsk.isEmpty()){
            ActivityCompat.requestPermissions(activity, needToAsk.toArray(new String[needToAsk.size()]), Permission_Request_Code);
            return false;
        }
        return true;
    }
    /**权限申请**/


    public static boolean isIgnorePermisson(String permission, Activity activity){
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public static boolean isCheckPermission(String permission, Activity activity){
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
