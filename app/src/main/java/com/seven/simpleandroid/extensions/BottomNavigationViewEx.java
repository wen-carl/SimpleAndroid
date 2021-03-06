package com.seven.simpleandroid.extensions;

import android.annotation.SuppressLint;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationViewEx {
    public static BottomNavigationMenuView getMenuView(BottomNavigationView navView) throws NoSuchFieldException, IllegalAccessException {
        return ReflectEx.getField(navView, "mMenuView");
    }

    public static void enableShiftMode(BottomNavigationView navView, boolean enable) throws NoSuchFieldException, IllegalAccessException {
        BottomNavigationMenuView menuView = getMenuView(navView);
        ReflectEx.setField(menuView, "mShiftingMode", enable);
    }

    @SuppressLint("RestrictedApi")
    public static void enableMenuItemShiftMode(BottomNavigationView navView, boolean enable) throws NoSuchFieldException, IllegalAccessException {
        BottomNavigationMenuView menuView = getMenuView(navView);
        BottomNavigationItemView[] mItems = ReflectEx.getField(menuView, "mButtons");
        if (null != mItems) {
            for (BottomNavigationItemView item : mItems) {
                ReflectEx.setField(item, "mShiftingMode", enable);
            }
            menuView.updateMenuView();
        }
    }
}
