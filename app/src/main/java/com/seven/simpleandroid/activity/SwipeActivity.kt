package com.seven.simpleandroid.activity

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.NestedScrollingChild3
import com.google.android.material.snackbar.Snackbar
import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.ClassAdapter
import com.seven.simpleandroid.interfaces.IItemClickListener
import com.seven.simpleandroid.model.ClassModel
import com.seven.simpleandroid.widget.ItemDecoration
import kotlinx.android.synthetic.main.activity_swipe.*
import kotlinx.coroutines.*
import java.util.*

class SwipeActivity : AppCompatActivity(), IItemClickListener<ClassModel> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bindData()
        bindEvent()
    }

    private fun bindData() {
        val data = mutableListOf<ClassModel>()
        for (i in 0..25) {
            val char = 'A' + i
            data.add(ClassModel(char.toString()))
        }

        val adapter = ClassAdapter(this, data)
        rvSwipe.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rvSwipe.addItemDecoration(ItemDecoration(this, drawableId = R.drawable.list_divider))
        rvSwipe.adapter = adapter

        swipeRefresh.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE)
    }

    private fun bindEvent() {
        swipeRefresh.setOnRefreshListener {
            GlobalScope.launch(context = Dispatchers.Default, block = {
                delay(3000)
                runOnUiThread {
                    loadMore()
                    swipeRefresh.isRefreshing = false
                }
            })
        }

        val adapter = rvSwipe.adapter as ClassAdapter
        adapter.itemClick = this
    }

    private fun loadMore() {
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
