package com.seven.simpleandroid.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seven.simpleandroid.R
import com.seven.simpleandroid.interfaces.IOnItemClickListener

class ImageAdapter(var images: List<String>, var onItemClickListener: IOnItemClickListener<String, ImageHolder>?) : androidx.recyclerview.widget.RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    constructor(images: List<String>) : this(images, null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        val holder = ImageHolder(view)

        view.setOnClickListener {
            onItemClickListener?.onItemClicked(holder, images[holder.adapterPosition])
        }

        return holder
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(images[position])
    }

    class ImageHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)!!

        fun bind(url: String) {
            Glide.with(imageView)
                .load(url)
                .apply(RequestOptions().placeholder(R.drawable.seven))
                .into(imageView)
        }
    }
}