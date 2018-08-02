package com.seven.simpleandroid.activity

import android.annotation.SuppressLint
import android.content.*
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.WifiDataAdapter
import com.seven.simpleandroid.model.WifiItem
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_wifi.*
import kotlinx.android.synthetic.main.nav_header_drawer1.*

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
                    "SSID: ${info.ssid} \nBSSID: ${info.bssid} \nIP: ${intToIp(info.ipAddress)} \nMAC: ${info.macAddress} \nRSSI: ${info.rssi} \n\n${info}"
        }

        rv_wifi.layoutManager = LinearLayoutManager(this)
        val adapter = WifiDataAdapter(getWifiItems(mManager.scanResults))
        rv_wifi.adapter = adapter

        adapter.setOnItemClickListener(object : WifiDataAdapter.IOnItemClickListener {
            override fun onItemClick(position: Int, info: WifiItem) {
                makeToast("position: $position" + "\n" + info.toString())

                if (info.isConnecting) {
                    disConnectItem()
                } else if (info.security == "open") {
                    connectItem(info, "")
                } else {
                    createPasswordDialog(info)
                }
            }
        })

        adapter.setOnItemLongClickListener(object : WifiDataAdapter.IOnItemLongClickListener {
            override fun onItemLongClick(position: Int, info: WifiItem) {
                makeToast("position: $position" + "\n" + info.toString())
            }
        })
    }

    private fun createPasswordDialog(item: WifiItem) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(item.ssid)
        builder.setIcon(R.mipmap.seven)
        builder.setMessage("请输入密码:")
        builder.setCancelable(false)
        val et = EditText(this)
        et.setLines(1)
        et.hint = "密码"
        builder.setView(et)

        builder.setPositiveButton("OK"
        ) { dialog, which ->
            val password = et.text.toString()
            if (password.length < 8) {
                makeToast("密码长度不够！请重新输入", "w")
            } else {
                connectItem(item, password)
                dialog.dismiss()
            }
        }
        builder.setNegativeButton("Cancle") { dialog, which ->
            //dialog.dismiss()
        }

        builder.create().show()
    }

    private fun connectItem(item: WifiItem, password: String) {
        val config = WifiConfiguration()
        config.BSSID = item.bssid
        config.SSID = "\"${item.ssid}\""
        config.allowedAuthAlgorithms.clear()
        config.allowedGroupCiphers.clear()
        config.allowedKeyManagement.clear()
        config.allowedPairwiseCiphers.clear()
        config.allowedProtocols.clear()

        when (item.security) {
            "open" -> {
                config.wepKeys[0] = ""
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                config.wepTxKeyIndex = 0
            }
            "wep" -> {
                if (!password.isEmpty()) {
                    if (isHexWepKey(password)) {
                        config.wepKeys[0] = password
                    } else {
                        config.wepKeys[0] = "\"$password\""
                    }
                }
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.wepTxKeyIndex = 0;
            }
            "wpa" -> {
                config.preSharedKey = "\"$password\""
                config.hiddenSSID = true
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                // 此处需要修改否则不能自动重联
                // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                config.status = WifiConfiguration.Status.ENABLED
            }
        }

        val id = mManager.addNetwork(config)

        if (-1 == id) {
            makeToast("net id error!", "e")
        } else {
            mManager.enableNetwork(id, true)
        }
    }

    @UiThread
    private fun disConnectItem() {
        mManager.disconnect()
    }

    private fun removeItem() {

    }

    private fun forgetItem() {

    }

    private fun getWifiItems(results: List<ScanResult>) : List<WifiItem> {
        val items = mutableListOf<WifiItem>()
        val connected = mManager.connectionInfo
        val conSsid = connected.ssid.removePrefix("\"").removeSuffix("\"")
        for (result in results) {
            val isConnected = result.SSID == conSsid
            val security = getSecurity(result.capabilities)
            val level = WifiManager.calculateSignalLevel(result.level, 5)
            val item = WifiItem(result.SSID, result.BSSID, level, security,  isConnected)
            items.add(item)
        }

        items.sort()
        return items
    }

    private fun makeToast(str : String, type: String = "i") {
        when (type) {
            "i" -> {
                Toasty.info(this, str).show()
            }
            "e" -> {
                Toasty.error(this, str).show()
            }
            "w" -> {
                Toasty.warning(this, str).show()
            }
        }
    }

    private fun isHexWepKey(wepKey: String): Boolean {
        val len = wepKey.length

        // WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
        return if (len != 10 && len != 26 && len != 58) {
            false
        } else isHex(wepKey)

    }

    private fun isHex(key: String): Boolean {
        for (i in key.length - 1 downTo 0) {
            val c = key[i]
            if (!(c in '0'..'9' || c in 'A'..'F' || c in 'a'..'f')) {
                return false
            }
        }

        return true
    }

    private fun intToIp(i: Int): String {
        // (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF)
        return (i and 0xFF).toString() + "." + (i shr 8 and 0xFF) + "." + (i shr 16 and 0xFF) + "." + (i shr 24 and 0xFF)
    }

    private fun getSecurity(capabilities: String) : String {
        return when {
            capabilities.contains("WPA") || capabilities.contains("wpa") -> "wpa"
            capabilities.contains("WEP") || capabilities.contains("wep") -> "wep"
            else -> "open"
        }
    }

    private inner class WifiStateChangedBroadcastReceiver : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
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
                    btn_start.isEnabled = true
                    val adapter = rv_wifi.adapter as WifiDataAdapter
                    adapter.data = getWifiItems(mManager.scanResults)

                    adapter.notifyDataSetChanged()
                }
                WifiManager.NETWORK_IDS_CHANGED_ACTION,
                WifiManager.SUPPLICANT_STATE_CHANGED_ACTION,
                WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION -> {
                    val adapter = rv_wifi.adapter as WifiDataAdapter
                    adapter.data = getWifiItems(mManager.scanResults)
                    adapter.notifyDataSetChanged()

                    val info = mManager.connectionInfo
                    if (null != info) {
                        tv_info.text = "SSID: ${info.ssid} \nBSSID: ${info.bssid} \nIP: ${intToIp(info.ipAddress)} \nMAC: ${info.macAddress} \nRSSI: ${info.rssi} \n\n${info}"
                    }
                }
                else -> {
                    makeToast(action)
                }
            }
        }
    }
}
