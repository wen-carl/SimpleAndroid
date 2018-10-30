package com.seven.simpleandroid.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.CommonPool
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch(context = CommonPool) {
            delay(1000)

            launch(UI) {
                startActivity(Intent(this@SplashActivity, ListActivity::class.java))
                finish()
            }
        }
    }
}
