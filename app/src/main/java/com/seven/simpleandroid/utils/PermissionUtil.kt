package com.seven.simpleandroid.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat

private const val PERMISSION_REQUEST_CODE = 1
private const val ACTIVITY_REQUEST_CODE = 1

class Permission(val activity: Activity) : Any() {

    private var onRequestPermissionResult: IOnRequestPermissionResult? = null

    constructor(activity: Activity, iOnRequestPermissionResult: IOnRequestPermissionResult) : this(activity) {
        onRequestPermissionResult = iOnRequestPermissionResult
    }

    fun setRequestPermissionCallback(iOnRequestPermissionResult: IOnRequestPermissionResult) : Permission {
        onRequestPermissionResult = iOnRequestPermissionResult

        return this
    }

    fun request(vararg permissions: String) : Permission {
        requestPermissions(activity, *permissions)

        return this
    }

    fun requestEach(vararg permissions: String) : Permission {
        for (permission in permissions) {
            requestPermissions(activity, permission)
        }

        return this
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (index in 0 until permissions.size) {
                val name = permissions[index]
                val result = grantResults[index]
                when {
                    PackageManager.PERMISSION_GRANTED == result -> onRequestPermissionResult?.onGranted(name)
                    shouldShowRequestPermissionRationale(activity, name) -> onRequestPermissionResult?.onDeniedFirst(name)
                    else -> onRequestPermissionResult?.onDenied(name)
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, vararg permissions: String, callBack: IOnRequestPermissionResult? = null) {
        for (permission in permissions) {
            val result = checkPermission(activity, permission)
            if (result) {
                if (null == callBack) {
                    onRequestPermissionResult?.onGranted(permission)
                } else {
                    callBack.onGranted(permission)
                }
            } else {
                if (null == callBack) {
                    onRequestPermissionResult?.onDenied(permission)
                } else {
                    callBack.onDenied(permission)
                }
            }
        }
    }

    companion object {

        fun checkPermission(context: Context, permission: String) : Boolean {

            return !isMOrAfter() || PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context.applicationContext, permission)
        }

        fun shouldShowRequestPermissionRationale(activity: Activity, permission: String) : Boolean {
            return isMOrAfter() && ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }

        fun isMOrAfter() : Boolean {
            return Build.VERSION_CODES.M <= Build.VERSION.SDK_INT
        }

        fun requestPermissions(activity: Activity, vararg permissions: String) {
            if (!isMOrAfter())
                return

            activity.requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }

        fun goToSettings(activity: Activity) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + activity.packageName)
            activity.startActivityForResult(intent, ACTIVITY_REQUEST_CODE)
        }
    }

    interface IOnRequestPermissionResult {
        fun onGranted(permission: String)
        fun onDenied(permission: String)
        fun onDeniedFirst(permission: String)
    }
}