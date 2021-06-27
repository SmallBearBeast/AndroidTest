package com.example.administrator.androidtest.Test.KVCompareTest

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.administrator.androidtest.App
import com.example.administrator.androidtest.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object DataStoreHelper {
    private const val TAG = "DataStoreHelper"
    private const val DATA_STORE_NAME = "DATA_STORE_NAME"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    private val Context.settingsDataStore: DataStore<Settings> by dataStore(
            fileName = "settings.pb",
            serializer = SettingsSerializer
    )

    fun getSettings(): Settings {
        return runBlocking {
            val settingsDataStore = App.getContext().settingsDataStore
            settingsDataStore.data.first()
        }
    }

    fun putSettings(settings: Settings) {
        GlobalScope.launch {
            val settingsDataStore = App.getContext().settingsDataStore
            settingsDataStore.updateData {
                Settings.newBuilder(settings).build()
            }
        }
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        var value = defaultValue
        runBlocking {
            val dataStore = App.getContext().dataStore
            dataStore.data.take(1).map { preferences ->
                preferences[intPreferencesKey(key)] ?: defaultValue
            }.collect {
                value = it
            }
        }
        return value
    }

    fun putInt(key: String, value: Int) {
        val dataStore = App.getContext().dataStore
        GlobalScope.launch {
            dataStore.edit { preferences ->
                preferences[intPreferencesKey(key)] = value
            }
        }
    }
}