package com.seven.simpleandroid.interfaces

import androidx.recyclerview.widget.RecyclerView

interface IOnItemClickListener<T, H : androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    fun onItemClicked(holder: H, model: T)
}