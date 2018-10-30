package com.seven.simpleandroid.adapter

import android.graphics.Color
import android.net.wifi.ScanResult
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.seven.simpleandroid.R
import com.seven.simpleandroid.model.WifiItem

class WifiDataAdapter(var data: List<WifiItem>) : RecyclerView.Adapter<WifiDataAdapter.WifiHolder>() {

    private var onItemClickListener: IOnItemClickListener? = null
    private var onItemLongClickListener: IOnItemLongClickListener? = null

    fun setOnItemClickListener(listener: IOnItemClickListener) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: IOnItemLongClickListener) {
        onItemLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wifi_info, parent, false)
        val holder = WifiHolder(view)

        if (null != onItemClickListener) {
            holder.itemView.setOnClickListener {
                onItemClickListener!!.onItemClick(
                    holder.adapterPosition,
                    data[holder.adapterPosition]
                )
            }
        }

        if (null != onItemLongClickListener) {
            holder.itemView.setOnLongClickListener {
                onItemLongClickListener!!.onItemLongClick(
                    holder.adapterPosition,
                    data[holder.adapterPosition]
                )

                true
            }
        }

        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WifiHolder, position: Int) {
        holder.bind(data[position])
    }

    class WifiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInfo = itemView.findViewById<TextView>(R.id.tv_info)
        val tvSecurity = itemView.findViewById<TextView>(R.id.tv_security)
        val ivWifi = itemView.findViewById<ImageView>(R.id.iv_wifi)

        fun bind(wifiInfo: WifiItem) {
            tvInfo.text = wifiInfo.ssid
            tvSecurity.text = wifiInfo.security

            if (wifiInfo.isConnecting) {
                tvInfo.setTextColor(Color.GREEN)
            } else {
                tvInfo.setTextColor(Color.BLACK)
            }

            var src = if (wifiInfo.security == "open") {
                when (wifiInfo.level) {
                    0 -> R.drawable.wifi_0_black_24dp
                    1 -> R.drawable.wifi_1_black_24dp
                    2 -> R.drawable.wifi_2_black_24dp
                    3 -> R.drawable.wifi_3_black_24dp
                    4 -> R.drawable.wifi_4_black_24dp
                    else -> R.drawable.wifi_0_black_24dp
                }
            } else {
                when (wifiInfo.level) {
                    0 -> R.drawable.wifi_0_lock_black_24dp
                    1 -> R.drawable.wifi_1_lock_black_24dp
                    2 -> R.drawable.wifi_2_lock_black_24dp
                    3 -> R.drawable.wifi_3_lock_black_24dp
                    4 -> R.drawable.wifi_4_lock_black_24dp
                    else -> R.drawable.wifi_0_lock_black_24dp
                }
            }

            ivWifi.setImageResource(src)
        }
    }

    interface IOnItemClickListener {
        fun onItemClick(position: Int, info: WifiItem)
    }

    interface IOnItemLongClickListener {
        fun onItemLongClick(position: Int, info: WifiItem)
    }
}