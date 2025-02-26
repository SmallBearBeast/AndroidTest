package com.example.administrator.androidtest.widget.pdfview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.administrator.androidtest.widget.pdfview.pdfapi.PdfApi
import com.example.administrator.androidtest.widget.pdfview.pdfapi.PdfApiFactory
import com.example.administrator.androidtest.widget.pdfview.pdfapi.PdfApiType
import com.example.administrator.androidtest.widget.pdfview.pdfapi.addDivider
import com.example.administrator.androidtest.widget.pdfview.pdfapi.isValid
import com.example.administrator.androidtest.widget.pdfview.pdfapi.md5
import com.example.liblog.SLog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class PdfRenderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var dividerHeight = 10
    private var currentTag = ""
    private val pdfApi = PdfApiFactory.create(context, PdfApiType.ANDROID)
    private val jobSet = hashSetOf<Job>()
    private var coroutineScope = getPdfRenderCoroutineScope()

    init {
        initView()
    }

    private fun initView() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addDivider(dividerHeight)
    }

    suspend fun load(path: String, tag: String = "") {
        try {
            currentTag = tag.ifEmpty {
                val md5 = File(path).md5()
                if (md5.isNotEmpty() && currentTag == md5) {
                    SLog.d(TAG, "load: skipping unchanged file, md5 = $md5")
                    return
                } else {
                    md5
                }
            }
            cancelJob()
            closePdf()
            pdfApi.load(File(path), currentTag)
            adapter = PdfItemAdapter()
            SLog.d(TAG, "load: PDF loaded from path successfully, currentTag = $currentTag, pageCount = ${pdfApi.getPageCount()}")
        } catch (e: Exception) {
            SLog.e(TAG, "load: exception message: ${e.message}")
        }
    }

    suspend fun load(uri: Uri, tag: String = "") {
        try {
            currentTag = tag.ifEmpty {
                val md5 = uri.md5(context)
                if (md5.isNotEmpty() && currentTag == md5) {
                    SLog.d(TAG, "load: skipping unchanged file, md5 = $md5")
                    return
                } else {
                    md5
                }
            }
            cancelJob()
            closePdf()
            pdfApi.load(uri, currentTag)
            adapter = PdfItemAdapter()
            SLog.d(TAG, "load: PDF loaded from uri successfully, currentTag = $currentTag, pageCount = ${pdfApi.getPageCount()}")
        } catch (e: Exception) {
            SLog.e(TAG, "load: exception message: ${e.message}")
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!coroutineScope.isActive) {
            coroutineScope = getPdfRenderCoroutineScope()
        }
    }

    override fun onDetachedFromWindow() {
        SLog.d(TAG, "onDetachedFromWindow： enter")
        super.onDetachedFromWindow()
        cancelJob()
        closePdf()
    }

    private fun cancelJob() {
        jobSet.forEach {
            it.cancel()
        }
        jobSet.clear()
    }

    private fun closePdf() {
        synchronized(PdfApi::class.java) {
            try {
                pdfApi.close()
            } catch (e: Exception) {
                SLog.e(TAG, "closePdf: exception message: ${e.message}")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDividerHeight(height: Int) {
        dividerHeight = height
        addDivider(height)
        adapter?.notifyDataSetChanged()
    }

    private fun getPdfRenderCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler { _, exception ->
        SLog.e(TAG, "catch PdfRenderView coroutine exception: ${exception.message}")
    })

    private inner class PdfItemAdapter : Adapter<PdfItemViewHolder>() {
        private var pdfPageCount = pdfApi.getPageCount()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfItemViewHolder {
            val iv = ImageView(parent.context)
            iv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            iv.scaleType = ImageView.ScaleType.CENTER_INSIDE
            return PdfItemViewHolder(iv)
        }

        override fun onBindViewHolder(holder: PdfItemViewHolder, position: Int) {
            holder.bindPdfItem(position)
        }

        override fun onViewRecycled(holder: PdfItemViewHolder) {
            super.onViewRecycled(holder)
            val bitmap = pdfApi.removeUsedBitmap(holder.layoutPosition)
            SLog.d(TAG, "onViewRecycled: layoutPosition = ${holder.layoutPosition}, bitmap = $bitmap")
            if (bitmap.isValid()) {
                pdfApi.putCachedBitmap(holder.layoutPosition, pdfApi.getBitmapSize(), bitmap)
            }
        }

        override fun getItemCount() = pdfPageCount
    }

    private inner class PdfItemViewHolder(itemView: View) : ViewHolder(itemView) {
        private val imageView = itemView as ImageView

        fun bindPdfItem(position: Int) {
            imageView.tag = position
            val cacheBitmap = pdfApi.getMemoryBitmap(position)
            if (cacheBitmap.isValid()) {
                SLog.d(TAG, "bindPdfItem: use memory cache: position = $position")
                imageView.setImageBitmap(cacheBitmap)
                return
            }
            SLog.d(TAG, "bindPdfItem: load bitmap start: position = $position")
            val job = coroutineScope.launch(CoroutineName("Job-$position")) {
                val bitmap = loadPdfBitmap(position)
                SLog.d(TAG, "bindPdfItem: load bitmap success: position = $position, tag = ${imageView.tag}, isValid = ${bitmap.isValid()}")
                if (bitmap.isValid()) {
                    if (imageView.tag == position) {
                        pdfApi.putUsedBitmap(position, pdfApi.getBitmapSize(), bitmap)
                        imageView.setImageBitmap(bitmap)
                    } else {
                        pdfApi.putCachedBitmap(position, pdfApi.getBitmapSize(), bitmap)
                    }
                }
            }
            job.invokeOnCompletion {
                if (it != null) {
                    SLog.d(TAG, "invokeOnCompletion: exception message: ${it.message}")
                }
                jobSet.remove(job)
            }
            jobSet.add(job)
        }

        private suspend fun loadPdfBitmap(position: Int): Bitmap? = withContext(Dispatchers.IO) {
            if (imageView.tag == position) {
                // java.lang.IllegalStateException: Current page not closed
                synchronized(PdfApi::class.java) {
                    val bitmap = pdfApi.getDiskOrNewBitmap(position)
                    return@withContext bitmap
                }
            }
            return@withContext null
        }
    }

    companion object {
        private const val TAG = "PdfRenderView"
    }
}
