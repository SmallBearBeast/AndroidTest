package com.example.administrator.androidtest.Base.ActAndFrag;

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
import com.example.administrator.androidtest.Common.Page.IPage;
import com.example.administrator.androidtest.Common.Page.Page;
import com.example.administrator.androidtest.Common.Page.PageProvider;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAct extends AppCompatActivity implements IPage {

    private static final String TAG = "BaseAct";
    private static final int Permission_Request_Code = 1;
    private static int runningCount = 0;

    private boolean foreground;
    protected BaseAct mActivity;
    protected Context mContext;
    private PermissionListener mPermissionListener;
    private ActivityResultListener mActivityResultListener;

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
        PageProvider.getInstance().addPage(this);
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
            if(mPermissionListener != null){
                mPermissionListener.onPermissionRequest(permissionSuccessArray, permissionFailArray);
            }
            onPermissionRequest(permissionSuccessArray, permissionFailArray);
        }
    }

    /**
     * 跳转activity
     */
    public Intent startActivity(Class clz, Bundle bundle){
        boolean isStart = bundle.getInt(IContext.START_ACTIVITY, 1) > 0;
        Intent intent = new Intent(this, clz);
        intent.putExtra(IContext.BUNDLE, bundle);
        if(isStart){
            ContextCompat.startActivity(this, intent, null);
        }
        return intent;
    }

    public Intent startActivityForResult(Class clz, int requestCode, Bundle bundle, ActivityResultListener listener){
        mActivityResultListener = listener;
        boolean isStart = bundle.getInt(IContext.START_ACTIVITY, 1) > 0;
        Intent intent = new Intent(this, clz);
        intent.putExtra(IContext.BUNDLE, bundle);
        if(isStart) {
            ActivityCompat.startActivityForResult(this, intent, requestCode, null);
        }
        return intent;
    }
    /**跳转activity**/

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

    public interface PermissionListener{
        void onPermissionRequest(List<String> permissionSuccessArray, List<String> permissionFailArray);
    }

    public void setPermissonListerner(PermissionListener listerner){
        mPermissionListener = listerner;
    }

    public interface ActivityResultListener{
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    public void setActivityResultListener(ActivityResultListener listerner){
        mActivityResultListener = listerner;
    }

    @Override
    public Page page() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mActivityResultListener != null){
            mActivityResultListener.onActivityResult(requestCode, resultCode, data);
        }
    }
}
