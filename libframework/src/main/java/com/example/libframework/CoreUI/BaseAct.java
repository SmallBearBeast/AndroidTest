package com.example.libframework.CoreUI;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.libframework.BuildConfig;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAct extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    private static final int Permission_Request_Code = 1;
    private PermissionListener mPermissionListener;
    private ActResultListener mActResultListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            handleIntent(intent, intent.getBundleExtra(IContext.BUNDLE));
        }
        if (BuildConfig.DEBUG) {
            getLifecycle().addObserver(new ActLifeDebug(TAG));
        }
        setContentView(layoutId());
    }


    public void goAct(Class clz) {
        goAct(clz, null, null);
    }

    public void goAct(Class clz, Bundle bundle) {
        goAct(clz, bundle, null);
    }

    public void goAct(Class clz, Bundle bundle, Bundle options) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(IContext.BUNDLE, bundle);
        ContextCompat.startActivity(this, intent, options);
    }

    public void goActForResult(Class clz, int requestCode, Bundle bundle, ActResultListener listener) {
        goActForResult(clz, requestCode, bundle, null, listener);
    }

    public void goActForResult(Class clz, int requestCode, Bundle bundle, Bundle options, ActResultListener listener) {
        mActResultListener = listener;
        Intent intent = new Intent(this, clz);
        intent.putExtra(IContext.BUNDLE, bundle);
        ActivityCompat.startActivityForResult(this, intent, requestCode, options);
    }

    public interface ActResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mActResultListener != null) {
            mActResultListener.onActivityResult(requestCode, resultCode, data);
            mActResultListener = null;
        }
    }

    public interface PermissionListener {
        void onPermissionRequest(List<String> permissionSuccessArray, List<String> permissionFailArray);
    }

    /**
     * Request permission
     *
     * @param permissions The requested permissions.
     * @param listener    The result listener of requested permission.
     * @return true: Do request permission. false: Have request permission and not to do.
     */
    public boolean requestPermissions(String[] permissions, PermissionListener listener) {
        mPermissionListener = listener;
        List<String> needToAsk = new ArrayList<>();
        for (String s : permissions) {
            if (!isCheckPermission(s)) {
                needToAsk.add(s);
            } else {
                if (isIgnorePermission(s)) {
                    needToAsk.add(s);
                }
            }
        }
        if (!needToAsk.isEmpty()) {
            ActivityCompat.requestPermissions(this, needToAsk.toArray(new String[needToAsk.size()]), Permission_Request_Code);
            return true;
        }
        mPermissionListener = null;
        return false;
    }

    public boolean isIgnorePermission(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }

    public boolean isCheckPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permission_Request_Code) {
            List<String> permissionSuccessArray = new ArrayList<>();
            List<String> permissionFailArray = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    permissionSuccessArray.add(permissions[i]);
                } else {
                    permissionFailArray.add(permissions[i]);
                }
            }
            if (mPermissionListener != null) {
                mPermissionListener.onPermissionRequest(permissionSuccessArray, permissionFailArray);
                mPermissionListener = null;
            }
        }
    }

    protected abstract int layoutId();

    protected void handleIntent(Intent intent, Bundle bundle) {

    }

    /**
     * Put shared data for easy access by other components.
     *
     * @param key   The name of shared data.
     * @param value The value of shared data.
     */
    protected void put(String key, Object value) {
        ViewModelProviders.of(this).get(ShareDataVM.class).put(key, value);
    }

    /**
     * Get the value corresponding to the key
     *
     * @param key The name of shared data.
     * @return The value of shared data.
     */
    protected <V> V get(String key) {
        return ViewModelProviders.of(this).get(ShareDataVM.class).get(key);
    }

    protected View getDecorView() {
        return getWindow().getDecorView();
    }
}
