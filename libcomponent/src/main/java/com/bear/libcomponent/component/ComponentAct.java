package com.bear.libcomponent.component;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.bear.libcomponent.base.BaseAct;

public abstract class ComponentAct extends BaseAct {
    private ComponentManager componentManager = new ComponentManager();
    
    protected <C extends IComponent> void regActComponent(C component, Object tag) {
        componentManager.regActComponent(this, component, tag);
    }

    protected <C extends IComponent> void regActComponent(C component) {
        componentManager.regActComponent(this, component);
    }

    public <C extends IComponent> C getComponent(Class<C> clz, Object tag) {
        return componentManager.getComponent(clz, tag);
    }

    public <C extends IComponent> C getComponent(Class<C> clz) {
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
