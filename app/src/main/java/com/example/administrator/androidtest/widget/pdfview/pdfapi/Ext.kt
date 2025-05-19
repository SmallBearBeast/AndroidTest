package com.example.administrator.androidtest.widget.pdfview.pdfapi

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bear.liblog.SLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.nio.channels.FileChannel
import java.security.MessageDigest

private const val TAG = "PdfApi-Ext"

internal fun Context.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

internal fun Context.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

internal fun Bitmap?.size(): Int {
    if (this == null || isRecycled) {
        return 0
    }
    try {
        return getAllocationByteCount()
    } catch (e: Exception) {
        SLog.e(TAG, "size: exception message: ${e.message}")
    }
    return getHeight() * getRowBytes()
}

internal fun Bitmap?.isValid(): Boolean {
    return this != null && !isRecycled
}

internal fun RecyclerView.addDivider(height: Int, color: Int = Color.TRANSPARENT) {
    for (index in itemDecorationCount - 1 downTo 1) {
        removeItemDecorationAt(index)
    }
    val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    val drawable = GradientDrawable()
    drawable.setSize(0, height)
    drawable.setColor(color)
    drawable.shape = GradientDrawable.RECTANGLE
    decoration.setDrawable(drawable)
    addItemDecoration(decoration)
}

internal suspend fun File.md5() = withContext(Dispatchers.IO) {
    getMd5FromFile(this@md5) ?: ""
}

internal suspend fun Uri.md5(context: Context) = withContext(Dispatchers.IO) {
    getMd5FromUri(context, this@md5) ?: ""
}

//suspend fun Uri.md5() = asd
private fun getMd5FromFile(file: File): String? {
    return try {
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
        bytesToHex(md5Digest.digest())
    } catch (e: Exception) {
        SLog.e(TAG, "getMd5FromFile: exception message: ${e.message}")
        null
    }
}

private fun getMd5FromUri(context: Context, uri: Uri): String? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { input ->
            val digest = MessageDigest.getInstance("MD5")
            val buffer = ByteArray(8 * 1024) // 8K分块读取，避免内存溢出
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                digest.update(buffer, 0, bytesRead)
            }
            bytesToHex(digest.digest()) // 将字节数组转为十六进制字符串
        }
    } catch (e: Exception) {
        SLog.e(TAG, "getMd5FromUri: exception message: ${e.message}")
        null
    }
}

// 字节数组转十六进制字符串工具函数
private fun bytesToHex(md5Bytes: ByteArray): String {
    // 优化的十六进制转换（查表法+预分配内存）
    val hexChars = "0123456789abcdef".toCharArray()
    return buildString(md5Bytes.size * 2) {
        md5Bytes.forEach { byte ->
            val unsignedByte = byte.toInt() and 0xFF
            append(hexChars[unsignedByte shr 4 and 0x0F])
            append(hexChars[unsignedByte and 0x0F])
        }
    }
}