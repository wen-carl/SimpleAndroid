package com.seven.simpleandroid.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.WifiDataAdapter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_wifi.*

class WifiActivity : AppCompatActivity() {

    private lateinit var mManager : WifiManager
    private val mReceiver = WifiStateChangedBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)
        mManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        initView()
    }

    override fun onStart() {
        super.onStart()

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        intentFilter.addAction(WifiManager.NETWORK_IDS_CHANGED_ACTION)
        intentFilter.addAction(WifiManager.ACTION_PICK_WIFI_NETWORK)
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)
        intentFilter.addAction(WifiManager.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE)
        registerReceiver(mReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(mReceiver)
    }

    @SuppressLint("SetTextI18n", "HardwareIds")
    private fun initView() {
        btn_open.text = if (mManager.isWifiEnabled) "Close" else "Open"
        btn_start.isEnabled = mManager.isWifiEnabled

        btn_open.setOnClickListener {
            mManager.isWifiEnabled = !mManager.isWifiEnabled
            btn_open.isEnabled = false
        }

        btn_start.setOnClickListener {
            if (mManager.isWifiEnabled) {
                mManager.startScan()
                btn_start.isEnabled = false
            }
        }

        val info = mManager.connectionInfo
        if (null != info) {
            tv_info.text =
                    "BSSID: ${info.bssid} \nIP: ${info.ipAddress} \nMAC: ${info.macAddress} \nRSSI: ${info.rssi} \nSSID: ${info.ssid} \n\n${info}"
        }

        rv_wifi.layoutManager = LinearLayoutManager(this)
        rv_wifi.adapter = WifiDataAdapter(mManager.scanResults)
    }

    private fun makeToast(str : String) {
        Toasty.info(this, str).show()
    }

    private inner class WifiStateChangedBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.action
            when (action) {
                WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                    val state = mManager.wifiState
                    when (state) {
                        WifiManager.WIFI_STATE_DISABLED -> {
                            makeToast("WIFI_STATE_DISABLED")
                            btn_open.isEnabled = true
                            btn_open.text = "Open"
                        }
                        WifiManager.WIFI_STATE_DISABLING -> {
                            makeToast("WIFI_STATE_DISABLING")
                        }
                        WifiManager.WIFI_STATE_ENABLED -> {
                            makeToast("WIFI_STATE_ENABLED")
                            btn_open.isEnabled = true
                            btn_open.text = "Close"

                            btn_start.isEnabled = true
                        }
                        WifiManager.WIFI_STATE_ENABLING -> {
                            makeToast("WIFI_STATE_ENABLING")
                        }
                        WifiManager.WIFI_STATE_UNKNOWN -> {
                            makeToast("WIFI_STATE_UNKNOWN")
                        }
                    }
                }
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION -> {
                    val adapter = rv_wifi.adapter as WifiDataAdapter
                    adapter.data = mManager.scanResults
                    adapter.notifyDataSetChanged()
                }
                else -> {
                    makeToast(action)
                }
            }
        }
    }
}
