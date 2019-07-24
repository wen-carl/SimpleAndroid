package com.seven.simpleandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.seven.simpleandroid.activity.getEventName

class MyView(context: Context, attributes: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : View(context, attributes, defStyleAttr, defStyleRes) {

    constructor(context: Context, attributes: AttributeSet?) : this(context, attributes, 0, 0)

    constructor(context: Context) : this(context, null)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        //var result = super.onTouchEvent(ev)
        var result = true
        println(String.format("MyView->onTouchEvent: action->${getEventName(ev?.action)} X->${ev?.x} Y->${ev?.y} result->$result"))
        return result
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var result = super.dispatchTouchEvent(ev)
        println(String.format("MyView->dispatchTouchEvent: action->${getEventName(ev?.action)} X->${ev?.x} Y->${ev?.y} result->$result"))
        return result
    }
}