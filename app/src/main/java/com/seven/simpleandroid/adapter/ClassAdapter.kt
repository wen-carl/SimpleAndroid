package com.seven.simpleandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class RVBaseAdapter<T>(var context: Context, var data: List<T> ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {

    }

    override fun getItemCount(): Int {
        return data.size
    }
}