package com.bear.libbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.bear.libbase.BackPressedHelper;
import com.example.libbase.R;

/**
 * Request Permission可以使用PermissionX库来代替。
 * implementation 'com.guolindev.permissionx:permissionx:1.7.1'
 * StartActivityResult 可以用androidx的ActivityResultLauncher来代替。
 * showProgress和showDialog应该放在专门的util类来实现。
 */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();

    private Toolbar toolbar;
    private VB viewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            handleIntent(intent);
        }
        if (layoutId() != -1) {
            setContentView(layoutId());
        } else {
            viewBinding = inflateViewBinding(getLayoutInflater());
            if (viewBinding != null) {
                setContentView(viewBinding.getRoot());
            } else {
                throw new RuntimeException("Must have a layout id or ViewBinding when init activity");
            }
        }
        toolbar = findViewById(R.id.lib_base_toolbar_id);
        if (toolbar != null) {
            // 若没有设置setSupportActionbar，则onCreateOptionsMenu不会回调。
            // 若调用setSupportActionBar，则在onCreateOptionsMenu(Menu menu)中获取menu引用，否则直接Menu menu = mToolbar.getMenu();
            setSupportActionBar(toolbar);
        }
        initViews();
    }

    /**
     * This method is called when clicking the back button.
     */
    public void addBackPressedListener(@NonNull BackPressedHelper.BackPressedListener listener) {
        BackPressedHelper.addBackPressedListener(this, listener);
    }

    protected void handleIntent(@NonNull Intent intent) {

    }

    protected void initViews() {

    }

    public @NonNull View getDecorView() {
        return getWindow().getDecorView();
    }

    public @Nullable Toolbar getToolbar() {
        return toolbar;
    }

    public @Nullable VB getBinding() {
        return viewBinding;
    }

    public @NonNull VB requireBinding() {
        return viewBinding;
    }

    protected int layoutId() {
        return -1;
    }

    protected VB inflateViewBinding(@NonNull LayoutInflater inflater) {
        return null;
    }
}
