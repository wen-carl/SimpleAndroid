package com.seven.simpleandroid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.seven.simpleandroid.R
import com.seven.simpleandroid.model.ImgModel
import com.seven.simpleandroid.model.ImgSourceType
import java.io.File

class MultipleTabAdapter(val data: List<ImgModel>) : RecyclerView.Adapter<MultipleTabAdapter.ViewHolder>() {

    var onItemClickListener: IOnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleTabAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_multiple_tab, parent, false)
        val holder = ViewHolder(itemView)

        itemView.setOnClickListener {
            onItemClickListener?.onItemClicked(data[holder.adapterPosition], holder)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MultipleTabAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    interface IOnItemClickListener {
        fun onItemClicked(model: ImgModel, holder: ViewHolder)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val img = itemView.findViewById<ImageView>(R.id.img)
        val tvInfo = itemView.findViewById<TextView>(R.id.tv_info)

        fun bind(model: ImgModel) {
            tvInfo.text = (model.txt + "$adapterPosition " + model.type)
            when (model.type) {
                ImgSourceType.Heap -> {
                    Glide.with(itemView.context)
                        .load(model.id)
                        .into(img)
                }
                ImgSourceType.Disk -> {
                    Glide.with(itemView.context)
                        .load(File(itemView.context.getExternalFilesDir(null), model.url))
                        .into(img)
                }
                ImgSourceType.Net -> {
                    Glide.with(itemView.context)
                        .load(model.url)
                        .into(img)
                }
            }
        }
    }
}