package com.seven.simpleandroid.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View

open class BaseFragment : androidx.fragment.app.Fragment() {

    companion object {
        val TAG = BaseFragment::class.java.simpleName
    }

    protected var isViewCreated = false
    protected var isLoadNeed = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }
}