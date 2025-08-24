package com.bear.libcomponent.provider

import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View

interface IMenuProvider {
    fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater): Boolean {
        return true
    }

    fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {

    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    fun onContextItemSelected(item: MenuItem): Boolean {
        return true
    }
}
