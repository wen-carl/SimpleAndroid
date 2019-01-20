package com.seven.simpleandroid.extensions

import android.annotation.SuppressLint
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

@SuppressLint("RestrictedApi")
fun BottomNavigationView.enableShiftMode(enable: Boolean) {
    val menuView = getMenuView()
    menuView?.labelVisibilityMode = com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED

    menuView?.updateMenuView()
}

@SuppressLint("RestrictedApi")
fun BottomNavigationView.enableMenuItemShiftMode(enable: Boolean) {
    val menuView = this.getMenuView()
    val mButtons = menuView?.getField<Array<BottomNavigationItemView>>("buttons")
    if (null != mButtons) {
        for (btn in mButtons) {
            btn.setField("isShifting", enable)
        }
        menuView.updateMenuView()
    }
}

fun BottomNavigationView.getMenuView() : BottomNavigationMenuView? {
    return getField<BottomNavigationMenuView>("menuView")
}