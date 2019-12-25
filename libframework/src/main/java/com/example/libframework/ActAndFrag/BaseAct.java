package com.example.libframework.ActAndFrag;

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

import com.example.libbase.Util.AppInitUtil;
import com.example.libframework.Page.IPage;
import com.example.libframework.Page.Page;
import com.example.libframework.Page.PageProvider;
import com.example.libframework.PageShareData.PageKey;
import com.example.libframework.PageShareData.PageShareDataHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAct extends AppCompatActivity implements IPage {

    public static BaseAct sAct = null;
    protected final String TAG = getClass().getSimpleName();
    private static final int Permission_Request_Code = 1;
    private static int sVisibleCount = 0;
    private boolean foreground;
    private Page mPage;
    private PageKey mPageKey;
    protected BaseAct mActivity;
    protected Context mContext;
    private PermissionListener mPermissionListener;
    private ActResultListener mActResultListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sAct = this;
        if(isSupportPageShareData()){
            mPageKey = PageShareDataHelper.createPageKey(getClass().getSimpleName());
            PageShareDataHelper.getInstance().markNewPage(mPageKey);
        }
        Intent intent = getIntent();
        if(intent != null){
            handleIntent(intent, intent.getBundleExtra(IContext.BUNDLE));
        }
        mActivity = this;
        mContext = this;
        super.onCreate(savedInstanceState);
        AppInitUtil.init(this);
    }

    @Override
    protected void onStart() {
        PageProvider.getInstance().addPage(createPage());
        sVisibleCount++;
        if (sVisibleCount == 1) {
            notifyForeground(true);
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        sVisibleCount--;
        if (sVisibleCount <= 0) {
            notifyForeground(false);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPage = null;
        if(isSupportPageShareData()) {
            PageShareDataHelper.getInstance().clear(mPageKey);
        }
        super.onDestroy();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof BaseFrag){
            ((BaseFrag)fragment).notifyForeground(foreground);
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
            if(mPermissionListener != null){
                mPermissionListener.onPermissionRequest(permissionSuccessArray, permissionFailArray);
            }
        }
    }

    /**
     * 通知应用是否在前后台，获取应用前后台状态
     */
    private void notifyForeground(boolean fore) {
        foreground = fore;
        if(!fore){
            PageProvider.getInstance().addPage(new Page(IPage.Background));
        }
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

    protected void onNotifyForeground(boolean fore) {
        Log.e(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyForeground: fore = " + fore);
    }

    public boolean isForeground() {
        return foreground;
    }
    /**通知应用是否在前后台，获取应用前后台状态**/

    /**
     * 跳转activity
     */
    public void goAct(Class clz, Bundle bundle) {
        goAct(clz, bundle, null);
    }

    public void goAct(Class clz, Bundle bundle, Bundle options){
        Intent intent = new Intent(this, clz);
        intent.putExtra(IContext.BUNDLE, bundle);
        ContextCompat.startActivity(this, intent, options);
    }

    public void goActForResult(Class clz, int requestCode, Bundle bundle, ActResultListener listener){
        goActForResult(clz, requestCode, bundle, null, listener);
    }

    public void goActForResult(Class clz, int requestCode, Bundle bundle, Bundle options, ActResultListener listener){
        mActResultListener = listener;
        Intent intent = new Intent(this, clz);
        intent.putExtra(IContext.BUNDLE, bundle);
        ActivityCompat.startActivityForResult(this, intent, requestCode, options);
    }
    /**跳转activity**/

    /**
     * 权限监听回调
     */
    public interface PermissionListener{
        void onPermissionRequest(List<String> permissionSuccessArray, List<String> permissionFailArray);
    }

    public boolean requestPermissions(String[] permissions, PermissionListener listerner){
        mPermissionListener = listerner;
        List<String> needToAsk = new ArrayList<>();
        for (String s : permissions) {
            if(!isCheckPermission(s)){
                needToAsk.add(s);
            }else {
                if(isIgnorePermisson(s)){
                    needToAsk.add(s);
                }
            }
        }
        if(!needToAsk.isEmpty()){
            ActivityCompat.requestPermissions(this, needToAsk.toArray(new String[needToAsk.size()]), Permission_Request_Code);
            return false;
        }
        return true;
    }

    public boolean isIgnorePermisson(String permission){
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }

    public boolean isCheckPermission(String permission){
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
    /**权限监听回调**/

    /**
     * 页面处理相关方法
     */
    private Page createPage(){
        mPage = new Page(pageId());
        return mPage;
    }

    public Page getPage(){
        return mPage;
    }

    @Override
    public int pageId() {
        return IPage.VpFragVisibilityAct;
    }
    /**页面处理相关方法**/

    /**
     * Activity需要实现的方法
     */
    protected abstract int layoutId();

    protected abstract void init(Bundle savedInstanceState);
    /**Activity需要实现的方法**/

    protected void handleIntent(Intent intent, Bundle bundle){

    }

    protected boolean isSupportPageShareData(){
        return false;
    }

    public interface ActResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mActResultListener != null){
            mActResultListener.onActivityResult(requestCode, resultCode, data);
            mActResultListener = null;
        }
    }
}
