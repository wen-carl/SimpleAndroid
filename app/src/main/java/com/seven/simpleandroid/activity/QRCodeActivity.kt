package com.seven.simpleandroid.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.zxing.integration.android.IntentIntegrator
import com.seven.simpleandroid.R
import com.seven.simpleandroid.utils.QRCodeUtil
import kotlinx.android.synthetic.main.activity_qrcode.*

class QRCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_scan_in.setOnClickListener {
            IntentIntegrator(this)
                .setBeepEnabled(false)
                .setCaptureActivity(ScanActivity::class.java)
                .setCameraId(0)
                .setPrompt("请将摄像头对准二维码")
                .initiateScan()
        }

        btn_scan.setOnClickListener {
            IntentIntegrator(this)
                .setBeepEnabled(false)
                .setCameraId(0)
                .setPrompt("请将摄像头对准二维码")
                .initiateScan()
        }

        btn_create.setOnClickListener {
            iv_qrcode.setImageBitmap(QRCodeUtil.createQRCodeBitmap(et_info.text.toString(), Math.min(iv_qrcode.width, iv_qrcode.height), Color.BLUE, Color.WHITE))
            //iv_qrcode.setImageBitmap(QRCodeUtil.createBarcode(et_info.text.toString(), iv_qrcode.width, iv_qrcode.height / 2))
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> super.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
