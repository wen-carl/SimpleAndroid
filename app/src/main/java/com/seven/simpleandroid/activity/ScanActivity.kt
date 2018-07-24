package com.seven.simpleandroid.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.seven.simpleandroid.R

class ScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
