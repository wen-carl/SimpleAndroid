package com.seven.simpleandroid.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.seven.simpleandroid.R
import com.seven.simpleandroid.utils.Permission
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : AppCompatActivity(), Permission.IOnRequestPermissionResult {

    private val TAG = PermissionActivity::class.java.simpleName

    private lateinit var mPermission: Permission

    private var PERMISSION_REQUEST = arrayOf(android.Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        mPermission = Permission(this, this)

        btn_check.setOnClickListener {
            val result = Permission.checkPermission(this, PERMISSION_REQUEST[0])
            log("check: $result")
        }

        btn_request.setOnClickListener {
            mPermission.request(*PERMISSION_REQUEST)
        }

        btn_setting.setOnClickListener {
            Permission.goToSettings(this)
        }
    }

    override fun onGranted(permission: String) {
        log("onGranted: $permission")
    }

    override fun onDenied(permission: String) {
        log("onDenied: $permission")
    }

    override fun onDeniedFirst(permission: String) {
        log("onDeniedFirst: $permission")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPermission.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPermission.onActivityResult(requestCode, resultCode, data, *PERMISSION_REQUEST)
    }

    fun log(msg: String) {
        Log.i(TAG, msg)
    }
}
