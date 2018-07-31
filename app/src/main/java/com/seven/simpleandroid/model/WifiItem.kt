package com.seven.simpleandroid.model

class WifiItem(var ssid: String, var bssid: String, var level: Int, var security: String, var isConnecting: Boolean) : Comparable<WifiItem> {

    constructor() : this("null", "null", 0, "null", false) {}

    override fun compareTo(other: WifiItem): Int {
        if (isConnecting) {
            return -1
        } else if (other.isConnecting) {
            return 1
        }

        return other.level - level
    }

    override fun toString(): String {
        return "SSID: $ssid \nsecurity: $security\nlevel: $level"
    }
}