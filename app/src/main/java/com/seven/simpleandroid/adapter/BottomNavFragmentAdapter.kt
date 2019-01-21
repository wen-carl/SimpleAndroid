package com.seven.simpleandroid.adapter

import com.seven.simpleandroid.fragment.BottomNavFragment

class BottomNavFragmentAdapter(fm: androidx.fragment.app.FragmentManager, val data: List<String>) : androidx.fragment.app.FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return BottomNavFragment.newInstance(data[position])
    }

    override fun getCount(): Int {
        return data.size
    }
}