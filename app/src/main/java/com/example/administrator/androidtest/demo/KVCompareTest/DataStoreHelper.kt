package com.example.administrator.androidtest.demo.KVCompareTest

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.administrator.androidtest.AndroidTestApplication
import com.example.administrator.androidtest.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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
            val settingsDataStore = AndroidTestApplication.context.settingsDataStore
            settingsDataStore.data.first()
        }
    }

    fun putSettings(settings: Settings) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        GlobalScope.launch {
            val settingsDataStore = AndroidTestApplication.context.settingsDataStore
            settingsDataStore.updateData {
                Settings.newBuilder(settings).build()
            }
        }
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        var value = defaultValue
        runBlocking {
            val dataStore = AndroidTestApplication.context.dataStore
            dataStore.data.take(1).map { preferences ->
                preferences[intPreferencesKey(key)] ?: defaultValue
            }.collect {
                value = it
            }
        }
        return value
    }

    fun putInt(key: String, value: Int) {
        val dataStore = AndroidTestApplication.context.dataStore
        GlobalScope.launch {
            dataStore.edit { preferences ->
                preferences[intPreferencesKey(key)] = value
            }
        }
    }

    fun getInt(key: String, defaultValue: Int, callback: (Int) -> Unit) {
        val dataStore = AndroidTestApplication.context.dataStore
        GlobalScope.launch {
            dataStore.data.take(1).map { preferences ->
                preferences[intPreferencesKey(key)]
            }.collect {
                callback(it ?: defaultValue)
            }
        }
    }
}