package com.seven.simpleandroid.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch(context = CommonPool) {
            delay(3000)

            launch(UI) {
                startActivity(Intent(this@SplashActivity, ListActivity::class.java))
                finish()
            }
        }
    }
}
