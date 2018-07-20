package com.seven.simpleandroid.extensions

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView

fun BottomNavigationView.enableShiftMode(enable: Boolean) {
    getMenuView()?.setField("mShiftingMode", enable)
}

@SuppressLint("RestrictedApi")
fun BottomNavigationView.enableMenuItemShiftMode(enable: Boolean) {
    val menuView = this.getMenuView()
    val mButtons = menuView?.getField<Array<BottomNavigationItemView>>("mButtons")
    if (null != mButtons) {
        for (btn in mButtons) {
            btn.setField("mShiftingMode", enable)
        }
        menuView.updateMenuView()
    }
}

fun BottomNavigationView.getMenuView() : BottomNavigationMenuView? {
    return getField<BottomNavigationMenuView>("mMenuView")
}