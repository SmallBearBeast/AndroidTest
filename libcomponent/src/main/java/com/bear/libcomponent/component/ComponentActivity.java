package com.bear.libcomponent.component;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.bear.libbase.activity.BaseActivity;

public abstract class ComponentActivity<VB extends ViewBinding> extends BaseActivity<VB> {
    private final ComponentManager componentManager = new ComponentManager();
    
    protected void regActComponent(ActivityComponent<VB> component, Object tag) {
        componentManager.regComponent(this, component, tag);
    }

    protected void regActComponent(ActivityComponent<VB> component) {
        componentManager.regComponent(this, component);
    }

    protected void regComponent(ViewComponent<ViewBinding> component, Object tag) {

    }

    protected void regComponent(ViewComponent<ViewBinding> component) {

    }

    public <C extends GroupComponent> C getComponent(Class<C> clz, Object tag) {
        return componentManager.getComponent(clz, tag);
    }

    public <C extends GroupComponent> C getComponent(Class<C> clz) {
        return componentManager.getComponent(clz);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        componentManager.dispatchOnBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return componentManager.dispatchOnCreateOptionsMenu(menu, getMenuInflater());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return componentManager.dispatchOnOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        componentManager.dispatchOnCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return componentManager.dispatchOnContextItemSelected(item);
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    @Override
    protected abstract VB inflateViewBinding(@NonNull LayoutInflater inflater);
}
