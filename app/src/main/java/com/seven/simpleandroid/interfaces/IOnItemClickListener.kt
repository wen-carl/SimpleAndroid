package com.seven.simpleandroid.interfaces

import android.support.v7.widget.RecyclerView

interface IOnItemClickListener<T, H : RecyclerView.ViewHolder> {
    fun onItemClicked(holder: H, model: T)
}