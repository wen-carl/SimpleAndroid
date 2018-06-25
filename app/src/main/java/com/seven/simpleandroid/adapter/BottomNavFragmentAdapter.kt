package com.seven.simpleandroid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.seven.simpleandroid.fragment.BottomNavFragment

class BottomNavFragmentAdapter(fm: FragmentManager, val data: List<String>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return BottomNavFragment.newInstance(data[position])
    }

    override fun getCount(): Int {
        return data.size
    }
}