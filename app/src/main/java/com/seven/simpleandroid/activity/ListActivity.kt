package com.seven.simpleandroid.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.seven.simpleandroid.R

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.list_activity)

        findView()
        bindData()
        bindEvent()
    }

    private fun findView() {

    }

    private fun bindData() {

    }

    private fun bindEvent() {

    }
}