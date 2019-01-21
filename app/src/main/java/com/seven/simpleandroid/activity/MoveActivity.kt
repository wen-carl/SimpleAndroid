package com.seven.simpleandroid.activity

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.seven.simpleandroid.R
import kotlinx.android.synthetic.main.activity_move.*

class MoveActivity : AppCompatActivity() {

    private var x = 0f
    private var y = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move)

        imageView.setOnTouchListener { it, e ->
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = e.rawX - it.x
                    y = e.rawY - it.y
                }
                MotionEvent.ACTION_MOVE -> {
                    it.x = e.rawX - x
                    it.y = e.rawY - y
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    x = 0f
                    y = 0f
                }
            }
            false
        }
    }
}
