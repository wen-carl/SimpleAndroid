package com.seven.simpleandroid.activity

import android.gesture.Gesture
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Switch
import com.seven.simpleandroid.R

class TouchEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_event)

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var result = super.dispatchTouchEvent(ev)
        println(String.format("TouchEventActivity->dispatchTouchEvent: action->${getEventName(ev?.action)} X->${ev?.x} Y->${ev?.y} result->$result"))
        return result
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        var result = super.onTouchEvent(ev)
        println(String.format("TouchEventActivity->onTouchEvent: action->${getEventName(ev?.action)} X->${ev?.x} Y->${ev?.y} result->$result"))
        return result
    }
}

fun getEventName(action: Int?): String {
    return when(action) {
        0 -> "ACTION_DOWN"
        1 -> "ACTION_UP"
        2 -> "ACTION_MOVE"
        3 -> "ACTION_CANCEL"
        4 -> "ACTION_OUTSIDE"
        else -> "ACTION_ELSE"
    }
}
