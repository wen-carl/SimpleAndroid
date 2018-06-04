package com.seven.simpleandroid.interfaces

import android.view.View

interface IItemClickListener<T> {
    fun itemClicked(view: View, position: Int, model: T)
    fun itemLongClicked(view: View, position: Int, model: T)
}