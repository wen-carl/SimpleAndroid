package com.seven.simpleandroid.activity

import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.NavUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.seven.simpleandroid.R
import com.seven.simpleandroid.R.id.*
import com.seven.simpleandroid.fragment.MultipleTabFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_multiple_tab.*
import kotlinx.android.synthetic.main.item_multiple_tab.view.*
import java.io.File

class MultipleTabActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_tab)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        initViewPager()
    }

    private fun initViewPager() {
        val titles = mutableListOf<String>()
        for (i in 'A'..'Z') {
            titles.add(i.toString())
        }

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, titles)
        viewpager.adapter = mSectionsPagerAdapter
        tabs.setupWithViewPager(viewpager)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_multiple_tab, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            android.R.id.home -> finish()
            R.id.action_settings -> return true
        }

        return super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, val data: List<String>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return MultipleTabFragment.newInstance(data[position])
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return data[position]
        }

        override fun getCount(): Int {
            return data.size
        }
    }
}
