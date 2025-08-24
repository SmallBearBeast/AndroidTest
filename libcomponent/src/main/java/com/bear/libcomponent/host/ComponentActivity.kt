package com.bear.libcomponent.host

import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.viewbinding.ViewBinding
import com.bear.libbase.activity.BaseActivity
import com.bear.libcomponent.core.IComponent
import com.bear.libcomponent.component.ui.ActivityComponent
import com.bear.libcomponent.component.ui.NonUIComponent
import com.bear.libcomponent.component.ui.ViewComponent
import com.bear.libcomponent.core.ComponentManager

// TODO: componentManager 在Activity和Fragment不公用
abstract class ComponentActivity<VB : ViewBinding> : BaseActivity<VB>() {
    internal val componentManager: ComponentManager = ComponentManager()

    override fun onBackPressed() {
        super.onBackPressed()
        componentManager.dispatchOnBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return componentManager.dispatchOnCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return componentManager.dispatchOnOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenuInfo) {
        componentManager.dispatchOnCreateContextMenu(menu, view, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return componentManager.dispatchOnContextItemSelected(item)
    }

    @JvmOverloads
    fun regComponent(component: ActivityComponent<VB>, tag: Any? = null) {
        componentManager.regComponent(this, component, tag)
    }

    @JvmOverloads
    fun regComponent(component: ViewComponent<*>, tag: Any? = null) {
        componentManager.regComponent(this, component, tag)
    }

    @JvmOverloads
    fun regComponent(component: NonUIComponent, tag: Any? = null) {
        componentManager.regComponent(this, component, tag)
    }

    @JvmOverloads
    fun <C : IComponent> getComponent(clz: Class<C>, tag: Any? = null): C? {
        return componentManager.getComponent(clz, tag)
    }

    abstract override fun inflateViewBinding(inflater: LayoutInflater): VB
}
