package com.example.administrator.androidtest.Base.ActAndFrag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.androidtest.Base.Dialog.PermissionDialog;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAct extends AppCompatActivity {

    private static final String TAG = "BaseAct";
    private static final int Permission_Request_Code = 1;
    private static int runningCount = 0;

    private boolean foreground;
    protected BaseAct mActivity;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            handleIntent(intent, intent.getBundleExtra(IContext.BUNDLE));
        }
        mActivity = this;
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        runningCount++;
        if (runningCount == 1) {
            notifyForeground(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        runningCount--;
        if (runningCount <= 0) {
            notifyForeground(false);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof BaseFrag){
            ((BaseFrag)fragment).notifyForeground(foreground);
        }
    }

    protected void notifyForeground(boolean fore) {
        foreground = fore;
        onNotifyForeground(fore);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if(frag instanceof BaseFrag){
                    ((BaseFrag) frag).notifyForeground(fore);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Permission_Request_Code){
            List<String> permissionSuccessArray = new ArrayList<>();
            List<String> permissionFailArray = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    permissionSuccessArray.add(permissions[i]);
                }else {
                    permissionFailArray.add(permissions[i]);
                }
            }
            if(!permissionFailArray.isEmpty()){
                // TODO: 2018/11/7 对话框
                PermissionDialog dialog = new PermissionDialog(this);
                dialog.show();
            }
            onPermissionRequest(permissionSuccessArray, permissionFailArray);
        }
    }

    /**
     * 跳转activity
     */
    public static Intent startActivity(Context context, Class clz, Bundle bundle){
        boolean isStart = bundle.getInt(IContext.START_ACTIVITY, 1) > 0;
        Intent intent = new Intent(context, clz);
        intent.putExtra(IContext.BUNDLE, bundle);
        if(isStart){
            ContextCompat.startActivity(context, intent, null);
        }
        return intent;
    }

    public static Intent startActivityForResult(Activity activity, Class clz, int requestCode, Bundle bundle){
        boolean isStart = bundle.getInt(IContext.START_ACTIVITY, 1) > 0;
        Intent intent = new Intent(activity, clz);
        intent.putExtra(IContext.BUNDLE, bundle);
        if(isStart) {
            ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
        }
        return intent;
    }
    /****/

    protected void onNotifyForeground(boolean fore) {
        Log.e(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyForeground: fore = " + fore);
    }

    public boolean isForeground() {
        return foreground;
    }

    protected void handleIntent(Intent intent, Bundle bundle){}

    protected abstract int layoutId();

    protected void init(Bundle savedInstanceState) {}

    protected void onPermissionRequest(List<String> permissionSuccessArray, List<String> permissionFailArray){}
}
