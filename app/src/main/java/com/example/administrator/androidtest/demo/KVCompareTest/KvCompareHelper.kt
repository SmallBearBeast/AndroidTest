package com.example.administrator.androidtest.demo.KVCompareTest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.administrator.androidtest.AndroidTestApplication
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

object KvCompareHelper {
    private const val TAG = "KvCompareHelper"
    private const val LOOP = 100000
    private const val KV_COMPARE_INT_KEY = "kv_compare_int_key_"
    private const val KV_COMPARE_SHORT_STRING_KEY = "kv_compare_short_string_key_"
    private const val KV_COMPARE_LONG_STRING_KEY = "kv_compare_long_string_key_"
    private const val KV_COMPARE_VERY_VERY_LONG_STRING_KEY = "kv_compare_very_very_long_string_key_"
    private const val KV_COMPARE_NAME = "kv_compare_name"
    private const val KV_COMPARE_SHORT_STRING = "kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_"
    private const val KV_COMPARE_LONG_STRING = "kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_kv_compare_long_string_"
    private var KV_COMPARE_VERY_VERY_LONG_STRING: String

    init {
        val byteArray = ByteArray(10 * 10000)
        for (i in byteArray.indices) {
            byteArray[i] = (i % 128).toByte()
        }
        KV_COMPARE_VERY_VERY_LONG_STRING = String(byteArray)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = KV_COMPARE_NAME)

    fun loadFromDataStore() {
        val startTs = System.currentTimeMillis()
        val dataStore = AndroidTestApplication.getContext().dataStore
        GlobalScope.launch(Dispatchers.IO) {
            dataStore.data.take(1).map { preferences ->
                preferences[intPreferencesKey(KV_COMPARE_INT_KEY + 0)]
                Log.d(TAG, "loadFromDataStore $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
            }.collect {

            }
        }
    }

    fun readFromDataStore() {
        val dataStore = AndroidTestApplication.getContext().dataStore
        GlobalScope.launch {
            dataStore.data.take(1).map { preferences ->
                val startTs = System.currentTimeMillis()
                for (index in 0 until LOOP) {
                    preferences[intPreferencesKey(KV_COMPARE_INT_KEY + index)]
                    preferences[stringPreferencesKey(KV_COMPARE_SHORT_STRING_KEY + index)]
                }
                Log.d(TAG, "readFromDataStore $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
            }.collect {

            }
        }
    }

    fun writeToDataStore() {
        val dataStore = AndroidTestApplication.getContext().dataStore
        GlobalScope.launch {
            dataStore.edit { settings ->
                val startTs = System.currentTimeMillis()
                settings[stringPreferencesKey(KV_COMPARE_VERY_VERY_LONG_STRING_KEY)] = KV_COMPARE_VERY_VERY_LONG_STRING
                for (index in 0 until LOOP) {
                    settings[intPreferencesKey(KV_COMPARE_INT_KEY + index)] = index
                    settings[stringPreferencesKey(KV_COMPARE_SHORT_STRING_KEY + index)] = KV_COMPARE_SHORT_STRING + index
                }
                Log.d(TAG, "writeToDataStore $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
            }
        }
    }

    fun clearDataStore() {
        val startTs = System.currentTimeMillis()
        val dataStore = AndroidTestApplication.getContext().dataStore
        GlobalScope.launch {
            dataStore.edit { settings ->
                settings.clear()
            }
        }
        Log.d(TAG, "clearDataStore $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun loadFromMMKV() {
        val startTs = System.currentTimeMillis()
        MMKV.mmkvWithID(KV_COMPARE_NAME)
        Log.d(TAG, "loadFromMMKV $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun readFromMMKV() {
        val mmkv = MMKV.mmkvWithID(KV_COMPARE_NAME)
        val startTs = System.currentTimeMillis()
        for (index in 0 until LOOP) {
            mmkv.getInt(KV_COMPARE_INT_KEY + index, 0)
            mmkv.getString(KV_COMPARE_SHORT_STRING_KEY + index, "")
        }
        Log.d(TAG, "readFromMMKV $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun writeToMMKV() {
        val mmkv = MMKV.mmkvWithID(KV_COMPARE_NAME)
        val startTs = System.currentTimeMillis()
        mmkv.putString(KV_COMPARE_VERY_VERY_LONG_STRING_KEY, KV_COMPARE_VERY_VERY_LONG_STRING)
        for (index in 0 until LOOP) {
            mmkv.putInt(KV_COMPARE_INT_KEY + index, index)
            mmkv.putString(KV_COMPARE_SHORT_STRING_KEY + index, KV_COMPARE_SHORT_STRING + index)
        }
        Log.d(TAG, "writeToMMKV $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun clearMMKV() {
        val startTs = System.currentTimeMillis()
        val mmkv = MMKV.mmkvWithID(KV_COMPARE_NAME)
        mmkv.clearMemoryCache()
        mmkv.clear()
        Log.d(TAG, "clearMMKV $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun loadFromSp() {
        val startTs = System.currentTimeMillis()
        val sp = AndroidTestApplication.getContext().getSharedPreferences(KV_COMPARE_NAME, Context.MODE_PRIVATE)
        sp.getInt(KV_COMPARE_INT_KEY + 0, 0)
        Log.d(TAG, "loadFromSp $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun readFromSp() {
        val sp = AndroidTestApplication.getContext().getSharedPreferences(KV_COMPARE_NAME, Context.MODE_PRIVATE)
        val startTs = System.currentTimeMillis()
        for (index in 0 until LOOP) {
            sp.getInt(KV_COMPARE_INT_KEY + index, 0)
            sp.getString(KV_COMPARE_SHORT_STRING_KEY + index, "")
        }
        Log.d(TAG, "readFromSp $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun writeToSp() {
        val sp = AndroidTestApplication.getContext().getSharedPreferences(KV_COMPARE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        val startTs = System.currentTimeMillis()
        editor.putString(KV_COMPARE_VERY_VERY_LONG_STRING_KEY, KV_COMPARE_VERY_VERY_LONG_STRING)
        for (index in 0 until LOOP) {
            editor.putInt(KV_COMPARE_INT_KEY + index, index)
            editor.putString(KV_COMPARE_SHORT_STRING_KEY + index, KV_COMPARE_SHORT_STRING + index)
        }
        editor.apply()
        Log.d(TAG, "writeToSp $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun clearSp() {
        val startTs = System.currentTimeMillis()
        val sp = AndroidTestApplication.getContext().getSharedPreferences(KV_COMPARE_NAME, Context.MODE_PRIVATE)
        sp.edit().clear().apply()
        Log.d(TAG, "clearSp $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    @SuppressLint("ApplySharedPref")
    fun testContinueSpApply() {
        for (i in 1..50) {
            val startTs = System.currentTimeMillis()
            val sp = AndroidTestApplication.getContext().getSharedPreferences(KV_COMPARE_NAME + i, Context.MODE_PRIVATE)
            val editor = sp.edit()
            for (index in 0 until 50) {
                editor.putString(KV_COMPARE_VERY_VERY_LONG_STRING_KEY + index, KV_COMPARE_VERY_VERY_LONG_STRING)
            }
            Log.d(TAG, "testContinueSpApply add $i cost: ${System.currentTimeMillis() - startTs}ms")
            editor.apply()
            Log.d(TAG, "testContinueSpApply write $i cost: ${System.currentTimeMillis() - startTs}ms")
        }
    }

    fun clearContinueSp() {
        for (i in 1..50) {
            val startTs = System.currentTimeMillis()
            val sp = AndroidTestApplication.getContext().getSharedPreferences(KV_COMPARE_NAME + i, Context.MODE_PRIVATE)
            sp.edit().clear().apply()
            Log.d(TAG, "clearContinueSp write $i cost: ${System.currentTimeMillis() - startTs}ms")
        }
    }
}
