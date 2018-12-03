package com.seven.simpleandroid.utils

import android.util.Log

class LogUtils {
    companion object {
        fun logV(tag: String, msg: String) {
            Log.v(tag, msg)
        }

        fun logMethodName(tag: String, msg: String) {
            Log.i(tag, msg + " " + getStackTraceInfo(level = 4))
        }

        /**
         * 取得堆栈信息
         * @param type  类型
         * @param level 级数
         * @return      方法名/类名
         */
        fun getStackTraceInfo(type: StackInfoType = StackInfoType.FunctionName, level: Int = 1): String {
            val stack = Throwable().stackTrace[level]
            return when (type) {
                StackInfoType.None -> ""
                StackInfoType.FunctionName -> stack.methodName
                StackInfoType.ClassName -> stack.className
                StackInfoType.AllInfo -> stack.className + " " + stack.methodName
            }
        }
    }

    enum class StackInfoType {
        None,
        FunctionName,
        ClassName,
        AllInfo
    }
}