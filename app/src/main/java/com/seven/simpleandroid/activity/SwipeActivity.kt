package com.seven.simpleandroid.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.ClassAdapter
import com.seven.simpleandroid.interfaces.IItemClickListener
import com.seven.simpleandroid.model.ClassModel
import kotlinx.android.synthetic.main.activity_swipe.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.*

class SwipeActivity : AppCompatActivity(), IItemClickListener<ClassModel> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        bindData()
        bindEvent()
    }

    fun bindData() {
        val data = mutableListOf<ClassModel>()
        for (i in 0..25) {
            val char = 'A' + i
            data.add(ClassModel(char.toString()))
        }

        val adapter = ClassAdapter(this, data)
        rvSwipe.layoutManager = LinearLayoutManager(this)
        rvSwipe.adapter = adapter
    }

    fun bindEvent() {
        swipeRefresh.setOnRefreshListener {
            launch(CommonPool) {
                delay(1000)
                runOnUiThread({
                    loadMore()
                    swipeRefresh.isRefreshing = false
                })
            }
        }

        val adapter = rvSwipe.adapter as ClassAdapter
        adapter.itemClick = this
    }

    fun loadMore() {
        val adapter = rvSwipe.adapter as ClassAdapter
        val count = Random().nextInt(26)
        for (i in 0.. + count) {
            adapter.data.add(ClassModel(('a' + i).toString()))
        }
        adapter.notifyDataSetChanged()
    }

    override fun itemClicked(view: View, position: Int, model: ClassModel) {
        Snackbar.make(rvSwipe, model.name + " Clicked!", Snackbar.LENGTH_SHORT).show()
    }

    override fun itemLongClicked(view: View, position: Int, model: ClassModel) {
        Snackbar.make(rvSwipe, model.name + " Long Clicked!", Snackbar.LENGTH_SHORT).show()
    }
}
