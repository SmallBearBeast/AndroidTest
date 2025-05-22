package com.example.administrator.androidtest.demo.ViewDemo.ToolbarTest;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;

import com.bear.libcommon.util.ToastUtil;
import com.bear.libcomponent.component.ActivityComponent;
import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;

public class ToolbarComponent extends ActivityComponent {

    public ToolbarComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        Toolbar toolbar = getActivity().getToolbar();
        if (toolbar != null) {
            toolbar.setTitle("Toolbar Test");
            ComponentActivity activity = getActivity();
            if (activity != null) {
                // 有使用setSupportActionBar()，则调用getSupportActionBar().setDisplayShowTitleEnabled(false);隐藏标题；若没设置，只要toolbar不设置title即可
                activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
            }
            toolbar.setNavigationOnClickListener(v -> ToastUtil.showToast("Click Navigation Icon"));
            // app:menu配置menu，不调用setSupportActionBar，setOnMenuItemClickListener回调也可以触发。
            // 通过onCreateOptionsMenu创建menu，setSupportActionBar需要在之前设置才能触发。
            toolbar.setOnMenuItemClickListener(item -> {
                ToastUtil.showToast("Click MenuItem: " + item.getTitle());
                return true;
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_class:
                ToastUtil.showToast("Click action_view_class");
                break;
            case R.id.action_search:
                ToastUtil.showToast("Click action_search");
                break;
            case R.id.action_notification:
                ToastUtil.showToast("Click action_notification");
                break;
            case R.id.action_item1:
                ToastUtil.showToast("Click action_item1");
                break;
            case R.id.action_item2:
                ToastUtil.showToast("Click action_item2");
                break;
        }
        return true;
    }
}
