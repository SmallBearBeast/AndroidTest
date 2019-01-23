package com.example.administrator.androidtest.Common.Util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.example.administrator.androidtest.Base.ActAndFrag.BaseAct;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    private static final int Permission_Request_Code = 1;

    /**
     * 权限申请
     */
    public static void requestPermissions(String[] permissions, BaseAct activity, BaseAct.PermissionListener listener){
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
            activity.setPermissonListerner(listener);
            ActivityCompat.requestPermissions(activity, needToAsk.toArray(new String[needToAsk.size()]), Permission_Request_Code);
        }
    }

    public static void requestPermissions(String[] permissions, Fragment fragment, BaseAct.PermissionListener listener){
        Activity activity = fragment.getActivity();
        if(activity instanceof BaseAct){
            BaseAct baseAct = (BaseAct) activity;
            requestPermissions(permissions, baseAct, listener);
        }
    }
    /**权限申请**/

    /**
     *
     */
    public static boolean isIgnorePermisson(String permission, Activity activity){
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public static boolean isCheckPermission(String permission, Activity activity){
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
