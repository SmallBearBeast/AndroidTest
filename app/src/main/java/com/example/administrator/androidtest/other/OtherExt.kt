package com.example.administrator.androidtest.other

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.nio.channels.FileChannel
import java.security.MessageDigest

suspend fun File.md5() = calculateMd5(this)

suspend fun calculateMd5(file: File): String = withContext(Dispatchers.IO) {
    val md5Digest = MessageDigest.getInstance("MD5")
    val maxChunkSize = 64 * 1024 * 1024L // 64MB
    val chunkSize = if (file.length() <= maxChunkSize) file.length() else maxChunkSize
    FileInputStream(file).use { fis ->
        val channel = fis.channel
        if (file.length() <= maxChunkSize) {
            // 小文件：一次性映射
            val buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
            md5Digest.update(buffer)
        } else {
            // 大文件：分块映射
            var position = 0L
            while (position < file.length()) {
                val size = minOf(chunkSize, file.length() - position)
                val buffer = channel.map(FileChannel.MapMode.READ_ONLY, position, size)
                md5Digest.update(buffer)
                position += size
            }
        }
    }

    val md5Bytes = md5Digest.digest()
    // 优化的十六进制转换（查表法+预分配内存）
    val hexChars = "0123456789abcdef".toCharArray()
    return@withContext buildString(32) {
        md5Bytes.forEach { byte ->
            val unsignedByte = byte.toInt() and 0xFF
            append(hexChars[unsignedByte shr 4 and 0x0F])
            append(hexChars[unsignedByte and 0x0F])
        }
    }
}