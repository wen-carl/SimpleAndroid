package com.seven.simpleandroid.base

import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {

    companion object {
        val TAG = BaseFragment::class.java.simpleName
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }
}