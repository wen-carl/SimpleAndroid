package com.seven.simpleandroid.interfaces

interface IOnItemClickListener<T, H : androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    fun onItemClicked(holder: H, model: T)
}