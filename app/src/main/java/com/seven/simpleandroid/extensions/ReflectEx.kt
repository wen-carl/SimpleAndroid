@file:Suppress("UNCHECKED_CAST")

package com.seven.simpleandroid.extensions

fun <T> Any.setField(mClass: Class<T>, field: String, value: Any) {
    val mField = mClass.getDeclaredField(field)
    mField.isAccessible = true
    mField.set(this, value)
}

fun Any.setField(field: String, value: Any) {
    setField(this::class.java, field, value)
}

fun <C, T> Any.getField(mClass: Class<C>, field: String) : T? {
    val mField = mClass.getDeclaredField(field)
    mField.isAccessible = true
    return mField.get(this) as T
}

fun <T> Any.getField(field: String) : T? {
    return getField(this::class.java, field)
}