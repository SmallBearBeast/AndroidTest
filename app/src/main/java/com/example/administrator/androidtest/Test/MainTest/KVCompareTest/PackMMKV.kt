package com.example.administrator.androidtest.Test.MainTest.KVCompareTest

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.tencent.mmkv.MMKV

@Suppress("UNCHECKED_CAST")
class PackMMKV(val name: String, sp: SharedPreferences) : SharedPreferences, SharedPreferences.Editor {
    private lateinit var valueMmkv: MMKV
    private lateinit var typeMmkv: MMKV
    private val modifiedSet = HashSet<String>()
    private val onSharedPreferenceChangeListenerSet = HashSet<SharedPreferences.OnSharedPreferenceChangeListener>()

    init {
        createMMKV()
        importFromSp(sp)
    }

    private fun importFromSp(sp: SharedPreferences) {
        if (sp.all.isNotEmpty()) {
            if (!getBoolean(SP_TRANSFER_TO_MMKV, false)) {
                Log.d(TAG, "SP_TRANSFER_TO_MMKV: name = $name, currentThread = ${Thread.currentThread()}")
                importValueMMKVFromSp(sp)
                importTypeMMKVFromSp(sp)
                putBoolean(SP_TRANSFER_TO_MMKV, true)
                // TODO: 2021/2/7 replace to remove file.
                sp.edit().clear().apply()
            }
        }
    }

    private fun createValueMMKV() {
        if (!this::valueMmkv.isInitialized) {
            val mmkvId = "$MMKV_VALUE$name"
            valueMmkv = MMKV.mmkvWithID(mmkvId)!!
        }
    }

    private fun createTypeMMKV() {
        if (!this::typeMmkv.isInitialized) {
            val mmkvTypeId = "$MMKV_TYPE_$name"
            typeMmkv = MMKV.mmkvWithID(mmkvTypeId)!!
        }
    }

    private fun createMMKV() {
        createValueMMKV()
        createTypeMMKV()
    }

    private fun importValueMMKVFromSp(sp: SharedPreferences) {
        valueMmkv.importFromSharedPreferences(sp)
    }

    private fun importTypeMMKVFromSp(sp: SharedPreferences) {
        val kvMap = sp.all
        kvMap.forEach { entry ->
            when (entry.value) {
                is Boolean -> typeMmkv.putInt(entry.key, TYPE_BOOLEAN)
                is Int -> typeMmkv.putInt(entry.key, TYPE_INT)
                is Long -> typeMmkv.putInt(entry.key, TYPE_LONG)
                is Float -> typeMmkv.putInt(entry.key, TYPE_FLOAT)
                is String -> typeMmkv.putInt(entry.key, TYPE_STRING)
                is Set<*> -> typeMmkv.putInt(entry.key, TYPE_STRING_SET)
                else -> {
                    Log.d(TAG, "importTypeMMKVFromSp: unknown type: ${entry.value?.javaClass}")
                }
            }
        }
    }

    private fun deleteMMKV() {
        valueMmkv.clear()
        typeMmkv.clear()
    }

    override fun contains(key: String?): Boolean {
        return valueMmkv.contains(key)
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return valueMmkv.getBoolean(key, defValue)
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        onSharedPreferenceChangeListenerSet.remove(listener)
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return valueMmkv.getInt(key, defValue)
    }

    override fun getAll(): MutableMap<String, *> {
        val keyArray = valueMmkv.allKeys()
        val kvMap = HashMap<String, Any>()
        keyArray?.apply {
            forEach {
                when (typeMmkv.getInt(it, 0)) {
                    TYPE_INT -> {
                        kvMap[it] = valueMmkv.getInt(it, 0)
                    }
                    TYPE_FLOAT -> {
                        kvMap[it] = valueMmkv.getFloat(it, 0F)
                    }
                    TYPE_BOOLEAN -> {
                        kvMap[it] = valueMmkv.getBoolean(it, false)
                    }
                    TYPE_LONG -> {
                        kvMap[it] = valueMmkv.getLong(it, 0L)
                    }
                    TYPE_STRING -> {
                        kvMap[it] = valueMmkv.getString(it, "") ?: ""
                    }
                    TYPE_STRING_SET -> {
                        kvMap[it] = valueMmkv.getStringSet(it, HashSet<String>())
                                ?: HashSet<String>()
                    }
                }
            }
        }
        Log.d(TAG, "getAll: kvMap = $kvMap")
        return kvMap
    }

    override fun edit(): SharedPreferences.Editor {
        return valueMmkv.edit()
    }

    override fun getLong(key: String?, defValue: Long): Long {
        return valueMmkv.getLong(key, defValue)
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        return valueMmkv.getFloat(key, defValue)
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        return valueMmkv.getStringSet(key, defValues)
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        if (listener != null) {
            onSharedPreferenceChangeListenerSet.add(listener)
        }
    }

    override fun getString(key: String?, defValue: String?): String? {
        return valueMmkv.getString(key, defValue)
    }

    override fun clear(): SharedPreferences.Editor {
        typeMmkv.clear()
        return valueMmkv.clear()
    }

    override fun putLong(key: String, value: Long): SharedPreferences.Editor {
        if (valueMmkv.decodeLong(key) == value) {
            return this
        }
        modifiedSet.add(key)
        typeMmkv.putInt(key, TYPE_LONG)
        return valueMmkv.putLong(key, value)
    }

    override fun putInt(key: String, value: Int): SharedPreferences.Editor {
        if (valueMmkv.decodeInt(key) == value) {
            return this
        }
        modifiedSet.add(key)
        typeMmkv.putInt(key, TYPE_INT)
        return valueMmkv.putInt(key, value)
    }

    override fun remove(key: String?): SharedPreferences.Editor {
        typeMmkv.remove(key)
        return valueMmkv.remove(key)
    }

    override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
        if (valueMmkv.decodeBool(key) == value) {
            return this
        }
        modifiedSet.add(key)
        typeMmkv.putInt(key, TYPE_BOOLEAN)
        return valueMmkv.putBoolean(key, value)
    }

    override fun putStringSet(key: String, value: MutableSet<String>?): SharedPreferences.Editor {
        if (valueMmkv.decodeStringSet(key) == value) {
            return this
        }
        modifiedSet.add(key)
        typeMmkv.putInt(key, TYPE_STRING_SET)
        return valueMmkv.putStringSet(key, value)
    }

    override fun commit(): Boolean {
        val result = valueMmkv.commit()
        notifyListeners()
        return result
    }

    override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
        if (valueMmkv.decodeFloat(key) == value) {
            return this
        }
        modifiedSet.add(key)
        typeMmkv.putInt(key, TYPE_FLOAT)
        return valueMmkv.putFloat(key, value)
    }

    override fun apply() {
        valueMmkv.apply()
        notifyListeners()
    }

    override fun putString(key: String, value: String?): SharedPreferences.Editor {
        if (valueMmkv.decodeString(key) == value) {
            return this
        }
        modifiedSet.add(key)
        typeMmkv.putInt(key, TYPE_STRING)
        return valueMmkv.putString(key, value)
    }

    private fun notifyListeners() {
        modifiedSet.forEach { key ->
            onSharedPreferenceChangeListenerSet.forEach { onSharedPreferenceChangeListener ->
                onSharedPreferenceChangeListener.onSharedPreferenceChanged(this, key)
            }
        }
        modifiedSet.clear()
    }

    companion object {
        private const val TAG = "GlipMMKV"
        private const val SP_TRANSFER_TO_MMKV = "sp_transfer_to_mmkv"
        private const val MMKV_VALUE = "mmkv_value_"
        private const val MMKV_TYPE_ = "mmkv_type_"
        private const val TYPE_INT = 1
        private const val TYPE_FLOAT = 2
        private const val TYPE_BOOLEAN = 3
        private const val TYPE_LONG = 4
        private const val TYPE_STRING = 5
        private const val TYPE_STRING_SET = 6
        private var initMMKV = false
        private val cacheGlipMMKVMap = HashMap<String, PackMMKV>()

        @JvmStatic
        fun getSharedPreferences(context: Context, name: String, sp: SharedPreferences): SharedPreferences {
            initMMKV(context)
            if (!cacheGlipMMKVMap.containsKey(name)) {
                cacheGlipMMKVMap[name] = PackMMKV(name, sp)
            }
            return cacheGlipMMKVMap[name]!!
        }

        @JvmStatic
        fun deleteSharedPreferences(context: Context, name: String): Boolean {
            initMMKV(context)
            deleteMMKV(name)
            return true
        }

        private fun initMMKV(context: Context) {
            if (!initMMKV) {
                Log.d(TAG, "initMMKV: initMMKV = $initMMKV")
                MMKV.initialize(context)
                initMMKV = true
            }
        }

        private fun deleteMMKV(name: String) {
            if (cacheGlipMMKVMap.containsKey(name)) {
                cacheGlipMMKVMap.remove(name)?.apply {
                    deleteMMKV()
                }
            }
        }
    }
}