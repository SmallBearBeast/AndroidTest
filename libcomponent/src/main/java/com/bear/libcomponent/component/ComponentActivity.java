package com.bear.libcomponent.component;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.libbase.activity.BaseActivity;

public abstract class ComponentActivity extends BaseActivity {
    private ComponentManager componentManager = new ComponentManager();
    
    protected void regActComponent(ActivityComponent component, Object tag) {
        componentManager.regComponent(this, component, tag);
    }

    protected void regActComponent(ActivityComponent component) {
        componentManager.regComponent(this, component);
    }

    protected void regComponent(ViewComponent component, Object tag) {

    }

    protected void regComponent(ViewComponent component) {

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
}
