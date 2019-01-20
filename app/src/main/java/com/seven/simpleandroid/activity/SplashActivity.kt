package com.seven.simpleandroid.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
