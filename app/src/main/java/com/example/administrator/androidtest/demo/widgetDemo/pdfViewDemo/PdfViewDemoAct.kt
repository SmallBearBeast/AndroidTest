package com.example.administrator.androidtest.demo.widgetDemo.pdfViewDemo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import com.bear.libcomponent.component.ComponentAct
import com.bear.librv.RvUtil
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.pdfview.PdfRenderView
import com.example.libcommon.Util.ToastUtil
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File

class PdfViewDemoAct : ComponentAct(), View.OnClickListener {
    private var pdfRenderView: PdfRenderView? = null

    override fun layoutId(): Int {
        return R.layout.act_pdf_view_test
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pdfRenderView = findViewById(R.id.pdfView)
        pdfRenderView?.setDividerHeight(100)
        findViewById<View>(R.id.openFilePicker).setOnClickListener(this)
        findViewById<View>(R.id.loadPdfButton_2).setOnClickListener(this)
        findViewById<View>(R.id.loadPdfButton_3).setOnClickListener(this)
        findViewById<View>(R.id.loadPdfButton_4).setOnClickListener(this)
        findViewById<View>(R.id.prePageButton).setOnClickListener(this)
        findViewById<View>(R.id.nextPageButton).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.openFilePicker -> openFilePicker()
            R.id.loadPdfButton_2 -> {}
            R.id.loadPdfButton_3 -> {}
            R.id.loadPdfButton_4 -> {}
            R.id.prePageButton -> prePage()
            R.id.nextPageButton -> nextPage()
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*" // 允许选择所有文件类型，或指定具体类型如 "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        // 确保至少有一个应用可以处理此 Intent
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(Intent.createChooser(intent, "选择文件"), REQUEST_CODE_PICK_FILE)
        } else {
            ToastUtil.showToast("未找到可用的文件管理器")
        }
    }

    private fun prePage() {
        var index = RvUtil.findFirstVisibleItemPosition(pdfRenderView)
        if (index > 0) {
            index = index - 1
            RvUtil.scrollToPos(pdfRenderView, index, true, 0)
        }
    }

    private fun nextPage() {
        var index = RvUtil.findFirstVisibleItemPosition(pdfRenderView)
        if (index < pdfRenderView!!.adapter!!.itemCount - 1) {
            index = index + 1
            RvUtil.scrollToPos(pdfRenderView, index, true, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK) {
            intent?.data?.let { uri ->
//                val name = getFileNameFromUri(this, uri)
//                val filePath = getFilePathFromUri(this, uri)
//                ToastUtil.showToast("$name---$filePath")
//                val fd = contentResolver.openFileDescriptor(uri, "r")
                MainScope().launch {
                    pdfRenderView?.load(uri)
                }
            }
        }
    }

    @SuppressLint("Range")
    private fun getFileNameFromUri(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        return null
    }

    @SuppressLint("Range")
    private fun getFilePathFromUri(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
            }
        }
        return null
    }

    companion object {
        private const val REQUEST_CODE_PICK_FILE = 100

        @JvmStatic
        fun go(context: Context) {
            val intent = Intent(context, PdfViewDemoAct::class.java)
            context.startActivity(intent)
        }
    }
}
