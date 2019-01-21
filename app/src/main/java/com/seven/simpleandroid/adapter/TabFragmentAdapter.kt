package com.seven.simpleandroid.adapter

import com.seven.simpleandroid.fragment.TabFragment

class TabFragmentAdapter(fm: androidx.fragment.app.FragmentManager, val data: List<String>) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return TabFragment.newInstance(data[position])
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position]
    }
}