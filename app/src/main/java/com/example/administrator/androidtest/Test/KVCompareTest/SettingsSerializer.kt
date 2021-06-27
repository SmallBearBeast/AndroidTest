package com.example.administrator.androidtest.Test.KVCompareTest

import androidx.datastore.core.Serializer
import com.example.administrator.androidtest.Settings
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer: Serializer<Settings> {
    override val defaultValue: Settings
        get() = Settings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Settings {
        return Settings.parseFrom(input)
    }

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        t.writeTo(output)
    }
}