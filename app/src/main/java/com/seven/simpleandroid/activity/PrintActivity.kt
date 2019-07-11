package com.seven.simpleandroid.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.seven.simpleandroid.R
import kotlinx.android.synthetic.main.activity_print.*
import android.print.PrintDocumentInfo
import android.graphics.pdf.PdfRenderer
import android.graphics.Bitmap
import android.print.pdf.PrintedPdfDocument
import android.graphics.pdf.PdfDocument
import android.graphics.Matrix
import android.graphics.Paint
import java.io.*


class PrintActivity : AppCompatActivity() {

    private val htmlStr = "<!DOCTYPE html><html><head><title>这是个标题</title></head><body><h1>这是个标题1</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p><h1>这是个标题2</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p><h1>这是个标题3</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p><h1>这是个标题4</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p><h1>这是个标题5</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p><h1>这是个标题6</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p><h1>这是个标题7</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p><h1>这是个标题8</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p><h1>这是个标题9</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p><h1>这是个标题10</h1><h1>这是一个一个简单的HTML</h1><p>Hello World！</p></body></html>"

    private val filePath = Environment.getExternalStorageDirectory().absolutePath + "/Download/Seven HTML.pdf"
    //Environment.ExternalStorageDirectory.AbsolutePath + "/Download/test.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnHtml.setOnClickListener {
            printHtml(htmlStr)
        }

        btnDoc.setOnClickListener {
            printDoc(filePath)
        }
    }

    private fun printHtml(html: String) {
        val webView = WebView(this);
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                createWebPrintJob(view!!)
            }
        }
        webView.loadDataWithBaseURL(null, html, "text/HTML", "UTF-8", null)
    }

    private fun createWebPrintJob(webView: WebView) {

        // Get a PrintManager instance
        (getSystemService(Context.PRINT_SERVICE) as? PrintManager)?.let { printManager ->

            val jobName = "${getString(R.string.app_name)} HTML"

            // Get a print adapter instance
            val printAdapter = webView.createPrintDocumentAdapter(jobName)

            // Create a print job with name and adapter instance
            printManager.print(
                    jobName,
                    printAdapter,
                    PrintAttributes.Builder().build()
            ).also { printJob ->
                // Save the job object for later status checking
                printManager.printJobs += printJob
            }
        }
    }

    private fun printDoc(docPath: String) {
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        // Set job name, which will be displayed in the print queue
        val jobName = "${getString(R.string.app_name)} Document"
        // Start a print job, passing in a PrintDocumentAdapter implementation
        // to handle the generation of a print document
        printManager.print(jobName, DocPrintAdapter2(this, docPath), null)
    }
}

/**
 * 直接利用文件输入输出流进行打印
 */
class DocPrintAdapter2(val context: Context, val filePath: String) : PrintDocumentAdapter() {

    override fun onLayout(oldAttributes: PrintAttributes?, newAttributes: PrintAttributes?, cancellationSignal: CancellationSignal?, callback: LayoutResultCallback?, extras: Bundle?) {
        if (cancellationSignal!!.isCanceled()) {
            callback?.onLayoutCancelled()
        } else {
            val builder = PrintDocumentInfo.Builder("filename.pdf");
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                    .build()
            callback?.onLayoutFinished(builder.build(), !(newAttributes == oldAttributes))
        }
    }

    override fun onWrite(pages: Array<out PageRange>?, destination: ParcelFileDescriptor?, cancellationSignal: CancellationSignal?, callback: WriteResultCallback?) {
        var input: InputStream? = null
        var out: OutputStream? = null
        try {
            val file = File(filePath)
            input = FileInputStream(file);
            out = FileOutputStream(destination?.fileDescriptor);

            val buf = ByteArray(16384)//Byte[16384]
            var size: Int
            size = input.read(buf)
            while (size >= 0 && !cancellationSignal!!.isCanceled) {
                out.write(buf, 0, size)
                size = input.read(buf)
            }

            if (cancellationSignal!!.isCanceled) {
                callback?.onWriteCancelled()
            } else {
                callback?.onWriteFinished(arrayOf<PageRange>(PageRange.ALL_PAGES))
            }
        } catch (e: Exception) {
            callback?.onWriteFailed(e.message)
            e.printStackTrace()
        } finally {
            try {
                input?.close()
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}

/***
 * 将pdf载入生成图片然后再打印
 */
class DocPrintAdapter1(val context: Context, val filePath: String) : PrintDocumentAdapter() {

    private var pageHeight: Int = 0
    private var pageWidth: Int = 0
    private var mPdfDocument: PdfDocument? = null
    private var totalpages = 1
    private val mlist: MutableList<Bitmap> = mutableListOf()

    override fun onLayout(oldAttributes: PrintAttributes?, newAttributes: PrintAttributes?, cancellationSignal: CancellationSignal?, callback: LayoutResultCallback?, extras: Bundle?) {
        mPdfDocument = PrintedPdfDocument(context, newAttributes!!) //创建可打印PDF文档对象

        //pageHeight = PrintAttributes.MediaSize.ISO_A4.getHeightMils() * 72 / 1000  //设置尺寸
        //pageWidth = PrintAttributes.MediaSize.ISO_A4.getWidthMils() * 72 / 1000
        pageHeight = newAttributes.mediaSize!!.heightMils * 72 / 1000  //设置尺寸
        pageWidth = newAttributes.mediaSize!!.widthMils * 72 / 1000

        if (true == cancellationSignal?.isCanceled()) {
            callback?.onLayoutCancelled()
            return
        }

        var mFileDescriptor: ParcelFileDescriptor? = null
        var pdfRender: PdfRenderer? = null
        var page: PdfRenderer.Page? = null
        try {
            mFileDescriptor = ParcelFileDescriptor.open(File(filePath), ParcelFileDescriptor.MODE_READ_ONLY)
            if (mFileDescriptor != null)
                pdfRender = PdfRenderer(mFileDescriptor)

            if (pdfRender!!.pageCount > 0) {
                totalpages = pdfRender.pageCount
                for (i in 0 until pdfRender.pageCount) {
                    page?.close()
                    page = pdfRender.openPage(i)

                    val bmp = Bitmap.createBitmap(page.width * 2, page.height * 2, Bitmap.Config.ARGB_8888)
                    page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                    mlist.add(bmp)
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            page?.close()
            mFileDescriptor?.close()
            pdfRender?.close()
        }

        if (totalpages > 0) {
            val builder = PrintDocumentInfo.Builder("快速入门.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalpages)  //构建文档配置信息

            val info = builder.build()
            callback?.onLayoutFinished(info, true)
        } else {
            callback?.onLayoutFailed("Page count is zero.")
        }
    }

    override fun onWrite(pages: Array<out PageRange>?, destination: ParcelFileDescriptor?, cancellationSignal: CancellationSignal?, callback: WriteResultCallback?) {
        for (i in 0 until totalpages) {
            if (pageInRange(pages!!.toList(), i)) //保证页码正确
            {
                val newPage = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create()
                val page = mPdfDocument?.startPage(newPage)  //创建新页面

                if (true == cancellationSignal?.isCanceled()) {  //取消信号
                    callback?.onWriteCancelled()
                    mPdfDocument?.close()
                    mPdfDocument = null
                    return
                }

                drawPage(page!!, i)  //将内容绘制到页面Canvas上
                mPdfDocument?.finishPage(page)
            }
        }

        try {
            mPdfDocument?.writeTo(FileOutputStream(destination?.getFileDescriptor()))
        } catch (e: IOException) {
            callback?.onWriteFailed(e.toString())
            return
        } finally {
            mPdfDocument?.close()
            mPdfDocument = null
        }

        callback?.onWriteFinished(pages)
    }

    private fun pageInRange(pageRanges: List<PageRange>, page: Int): Boolean {
        for (i in pageRanges) {
            if ((page >= i.start) && (page <= i.end))
                return true
        }
        return false
    }

    //页面绘制（渲染）
    private fun drawPage(page: PdfDocument.Page, pagenumber: Int) {
        val canvas = page.getCanvas()
        if (mlist.count() > 0) {
            val paint = Paint()
            val bitmap = mlist.get(pagenumber)
            val bitmapWidth = bitmap.getWidth()
            val bitmapHeight = bitmap.getHeight()
            // 计算缩放比例
            val scale = pageWidth.toFloat() / bitmapWidth.toFloat()
            // 取得想要缩放的matrix参数
            val matrix = Matrix()
            matrix.postScale(scale, scale)
            canvas.drawBitmap(bitmap, matrix, paint)
        }
    }

}
