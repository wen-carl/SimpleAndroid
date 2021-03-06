package com.seven.simpleandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.seven.simpleandroid.R
import com.seven.simpleandroid.interfaces.IItemClickListener
import com.seven.simpleandroid.model.ClassModel

class ClassAdapter(var context: Context, var data: MutableList<ClassModel> = mutableListOf()) : androidx.recyclerview.widget.RecyclerView.Adapter<ClassViewHolder>() {

    var itemClick: IItemClickListener<ClassModel>? = null

    init {

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_class, parent, false)
        return ClassViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val model = data[position]
        holder.name.text = model.name
        if (model.detail.isEmpty()) {
            holder.detail.visibility = View.GONE
        } else {
            holder.detail.visibility = View.VISIBLE
            holder.detail.text = model.detail
        }

        if (!holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener {
                itemClick?.itemClicked(it, holder.adapterPosition, data[holder.adapterPosition])
            }

            holder.itemView.setOnLongClickListener {
                itemClick?.itemLongClicked(it, holder.adapterPosition, data[holder.adapterPosition])
                return@setOnLongClickListener true
            }

        }
    }
}

class ClassViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    var name: TextView = itemView.findViewById(R.id.txt_name)
    var detail: TextView = itemView.findViewById(R.id.txt_detail)
}