package com.seven.simpleandroid.adapter

import android.net.wifi.ScanResult
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.seven.simpleandroid.R

class WifiDataAdapter(var data: List<ScanResult>) : RecyclerView.Adapter<WifiDataAdapter.WifiHolder>() {

    var onItemClickListener: IOnItemClickListener? = null

    constructor(data: List<ScanResult>, listener: IOnItemClickListener?) : this(data) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wifi_info, parent, false)

        return WifiHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WifiHolder, position: Int) {
        holder.bind(data[position])
        if (null != onItemClickListener && !holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener {
                onItemClickListener!!.onItemClick(holder.adapterPosition, data[holder.adapterPosition])
            }
        }
    }

    class WifiHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val tvInfo = itemView!!.findViewById<TextView>(R.id.tv_info)
        val ivWifi = itemView!!.findViewById<ImageView>(R.id.iv_wifi)

        fun bind(wifiInfo: ScanResult) {
            tvInfo.text = wifiInfo.SSID
            ivWifi.setImageLevel(0)
        }
    }

    interface IOnItemClickListener {
        fun onItemClick(position: Int, info: ScanResult)
    }
}