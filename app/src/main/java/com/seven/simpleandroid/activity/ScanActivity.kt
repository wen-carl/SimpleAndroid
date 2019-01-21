package com.seven.simpleandroid.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.seven.simpleandroid.R
import kotlinx.android.synthetic.main.activity_scan.*

class ScanActivity : AppCompatActivity() {

    private var mCaptureManager : CaptureManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        mCaptureManager = CaptureManager(this, barcode_view)
        mCaptureManager!!.initializeFromIntent(intent, savedInstanceState)
        mCaptureManager!!.decode()
    }

    override fun onResume() {
        super.onResume()
        mCaptureManager!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mCaptureManager!!.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mCaptureManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcode_view.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }
}
