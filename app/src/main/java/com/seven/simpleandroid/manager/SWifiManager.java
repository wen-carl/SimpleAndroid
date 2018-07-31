package com.seven.simpleandroid.manager;

class SWifiManager {
    private static final SWifiManager ourInstance = new SWifiManager();

    static SWifiManager getInstance() {
        return ourInstance;
    }

    private SWifiManager() {
    }
}
