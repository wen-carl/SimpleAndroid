package com.seven.simpleandroid.activity

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.seven.simpleandroid.Fragment.TabFragment
import com.seven.simpleandroid.R
import com.seven.simpleandroid.R.id.*
import com.seven.simpleandroid.adapter.TabFragmentAdapter
import kotlinx.android.synthetic.main.activity_nested.*

class NestedActivity : AppCompatActivity(), TabFragment.OnFragmentInteractionListener,
    TabLayout.OnTabSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        viewpager.adapter = TabFragmentAdapter(supportFragmentManager, listOf("One", "Two", "Three"))
        tab.setupWithViewPager(viewpager)

        tab.addOnTabSelectedListener(this)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        Toast.makeText(this, tab?.text ?: "" + " Reselected", Toast.LENGTH_SHORT).show()
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        toolbar_layout.title = tab?.text
    }

    override fun onFragmentInteraction(uri: Uri) {
        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
