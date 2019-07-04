package com.seven.simpleandroid.activity

import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.seven.simpleandroid.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val back = this@SplashActivity.window.decorView.background
        if (back is LayerDrawable) {
            val vectorDrawable = back.findDrawableByLayerId(R.id.animated_loading)
            if (vectorDrawable is Animatable) {
                vectorDrawable.start()
            }
        }

        GlobalScope.launch(context = Dispatchers.Default) {
            delay(1000)

            launch(Dispatchers.Main) {
                startActivity(Intent(this@SplashActivity, ListActivity::class.java))
                finish()
            }
        }
    }
}
