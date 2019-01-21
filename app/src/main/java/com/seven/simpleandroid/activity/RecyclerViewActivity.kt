package com.seven.simpleandroid.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.ClassAdapter
import com.seven.simpleandroid.model.ClassModel
import com.seven.simpleandroid.widget.ItemDecoration
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val manager = androidx.recyclerview.widget.LinearLayoutManager(this)
        manager.orientation = androidx.recyclerview.widget.RecyclerView.VERTICAL
        rvRecycler.layoutManager = manager

        val data = mutableListOf<ClassModel>()
        for (i in 0..50) {
            val char = 'A' + i
            data.add(ClassModel(char.toString()))
        }

        val adapter = ClassAdapter(this, data)
        rvRecycler.adapter = adapter
        rvRecycler.addItemDecoration(ItemDecoration(this, 4, R.drawable.list_divider))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recyclerview_type, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_vertical -> {
                val manager = androidx.recyclerview.widget.LinearLayoutManager(this)
                manager.orientation = androidx.recyclerview.widget.RecyclerView.VERTICAL
                rvRecycler.layoutManager = manager
            }
            R.id.action_horizontal -> {
                val manager = androidx.recyclerview.widget.LinearLayoutManager(this)
                manager.orientation = androidx.recyclerview.widget.RecyclerView.HORIZONTAL
                rvRecycler.layoutManager = manager
            }
            R.id.action_grid_v -> {
                val manager = androidx.recyclerview.widget.GridLayoutManager(this, 4)
                manager.orientation = androidx.recyclerview.widget.RecyclerView.VERTICAL
                rvRecycler.layoutManager = manager
            }
            R.id.action_grid_h -> {
                val manager = androidx.recyclerview.widget.GridLayoutManager(this, 4)
                manager.orientation = androidx.recyclerview.widget.RecyclerView.HORIZONTAL
                rvRecycler.layoutManager = manager
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }
}
