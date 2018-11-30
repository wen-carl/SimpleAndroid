package com.seven.simpleandroid.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(context = Dispatchers.Default, block = {
            delay(1000)

            launch(Dispatchers.Main) {
                startActivity(Intent(this@SplashActivity, ListActivity::class.java))
                finish()
            }
        })
    }
}
