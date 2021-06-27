package com.example.administrator.androidtest.Test.KVCompareTest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.administrator.androidtest.App
import com.example.libbase.Util.ExecutorUtil
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object KvCompareHelper {
    private const val TAG = "KvCompareHelper"
    private const val LOOP = 10
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
        val dataStore = App.getContext().dataStore
        GlobalScope.launch {
            dataStore.data.map { preferences ->
                preferences[intPreferencesKey(KV_COMPARE_INT_KEY + 0)]
                Log.d(TAG, "loadFromDataStore $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
            }.collect {

            }
        }
    }

    fun readFromDataStore() {
        val dataStore = App.getContext().dataStore
        GlobalScope.launch {
            dataStore.data.map { preferences ->
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
        val dataStore = App.getContext().dataStore
        GlobalScope.launch {
            dataStore.edit { settings ->
                val startTs = System.currentTimeMillis()
                settings[stringPreferencesKey(KV_COMPARE_VERY_VERY_LONG_STRING_KEY)] = KV_COMPARE_VERY_VERY_LONG_STRING
                for (index in 0 until LOOP) {
                    settings[intPreferencesKey(KV_COMPARE_INT_KEY + index)] = index
                    settings[stringPreferencesKey(KV_COMPARE_SHORT_STRING_KEY + index)] = KV_COMPARE_SHORT_STRING + index
                }
                Log.d(TAG, "writeFromDataStore $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
            }
        }
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
        Log.d(TAG, "writeFromMMKV $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun loadFromSp() {
        val startTs = System.currentTimeMillis()
        val sp = App.getContext().getSharedPreferences(KV_COMPARE_NAME, Context.MODE_PRIVATE)
        sp.getInt(KV_COMPARE_INT_KEY + 0, 0)
        Log.d(TAG, "loadFromSp $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun readFromSp() {
        val sp = App.getContext().getSharedPreferences(KV_COMPARE_NAME, Context.MODE_PRIVATE)
        val startTs = System.currentTimeMillis()
        for (index in 0 until LOOP) {
            sp.getInt(KV_COMPARE_INT_KEY + index, 0)
            sp.getString(KV_COMPARE_SHORT_STRING_KEY + index, "")
        }
        Log.d(TAG, "readFromSp $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun writeToSp() {
        val sp = App.getContext().getSharedPreferences(KV_COMPARE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        val startTs = System.currentTimeMillis()
        editor.putString(KV_COMPARE_VERY_VERY_LONG_STRING_KEY, KV_COMPARE_VERY_VERY_LONG_STRING)
        for (index in 0 until LOOP) {
            editor.putInt(KV_COMPARE_INT_KEY + index, index)
            editor.putString(KV_COMPARE_SHORT_STRING_KEY + index, KV_COMPARE_SHORT_STRING + index)
        }
        editor.apply()
        Log.d(TAG, "writeFromSp $LOOP int cost: ${System.currentTimeMillis() - startTs}ms")
    }

    fun readFromDataStore(key: String, defaultValue: Int, callback: (Int) -> Unit) {
        val dataStore = App.getContext().dataStore
        GlobalScope.launch {
            dataStore.data.map { preferences ->
                preferences[intPreferencesKey(key)]
            }.collect {
                callback(it ?: defaultValue)
            }
        }
    }

    fun writeToDataStore(key: String, value: Int) {
        val dataStore = App.getContext().dataStore
        GlobalScope.launch {
            dataStore.edit { preferences ->
                preferences[intPreferencesKey(key)] = value
            }
        }
    }

    fun readFromMMKV(key: String, defaultValue: Int): Int {
        return MMKV.mmkvWithID(KV_COMPARE_NAME).getInt(key, defaultValue)
    }

    fun writeToMMKV(key: String, value: Int) {
        MMKV.mmkvWithID(KV_COMPARE_NAME).putInt(key, value)
    }

    private const val TEST_SP_COUNT = 50
    @SuppressLint("ApplySharedPref")
    fun testSpCommit() {
        for (i in 1..TEST_SP_COUNT) {
            val startTs = System.currentTimeMillis()
            val sp = App.getContext().getSharedPreferences(KV_COMPARE_NAME + i, Context.MODE_PRIVATE)
            val editor = sp.edit()
            for (index in 0 until LOOP) {
                editor.putString(KV_COMPARE_VERY_VERY_LONG_STRING_KEY + index, KV_COMPARE_VERY_VERY_LONG_STRING)
            }
            Log.d(TAG, "testSpApply add $i cost: ${System.currentTimeMillis() - startTs}ms")
            editor.apply()
            Log.d(TAG, "testSpApply write $i cost: ${System.currentTimeMillis() - startTs}ms")
        }
    }

    fun clearTestSp() {
        for (i in 1..TEST_SP_COUNT) {
            val sp = App.getContext().getSharedPreferences(KV_COMPARE_NAME + i, Context.MODE_PRIVATE)
            sp.edit().clear().apply()
        }
    }

    fun preLoadTestSp() {
        ExecutorUtil.execute {
            for (i in 1..TEST_SP_COUNT) {
                val startTs = System.currentTimeMillis()
                val sp = App.getContext().getSharedPreferences(KV_COMPARE_NAME + i, Context.MODE_PRIVATE)
                val allMap = sp.all
                Log.d(TAG, "preLoadTestSp $i cost: ${System.currentTimeMillis() - startTs}ms")
            }
        }
    }
}
