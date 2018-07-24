package com.seven.simpleandroid.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.integration.android.IntentIntegrator
import com.seven.simpleandroid.R
import kotlinx.android.synthetic.main.activity_qrcode.*

class QRCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        btn_scan_in.setOnClickListener {
            startActivityForResult(Intent(this, ScanActivity::class.java), 100)
        }

        btn_scan.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }

        btn_create.setOnClickListener {
            iv_qrcode.setImageBitmap(createBitmap(et_info.text.toString()))
        }
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CAMERA), 0)
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (null == result) {
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            tv_info.text = result.contents
        }
    }

    fun createBitmap(str: String): Bitmap? {
        val multiFormatWriter = MultiFormatWriter()
        var bitmap : Bitmap? = null
        try {
            val matrix = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200)
            val width = matrix.width
            val height = matrix.height
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (matrix.get(x, y)) -0x1000000 else -0x1 //黑色和白色
                }
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        } catch (e: WriterException) {
            e.printStackTrace()
        }

        return bitmap
    }
}
