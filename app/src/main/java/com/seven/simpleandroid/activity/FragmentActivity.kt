package com.seven.simpleandroid.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.seven.simpleandroid.R
import com.seven.simpleandroid.fragment.PlusOneFragment
import kotlinx.android.synthetic.main.activity_recycler_view.*

class FragmentActivity : AppCompatActivity(), PlusOneFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        addFragment(0)
    }

    private fun addFragment(index: Int) {
        val transcation = supportFragmentManager.beginTransaction()
        transcation.setCustomAnimations(R.anim.left_in, R.anim.right_out, R.anim.right_in, R.anim.left_out)
        val fragment = PlusOneFragment.newInstance(index + 1)
        if (0 == index) {
            transcation.add(R.id.container, fragment, "$index")
        } else {
            transcation.replace(R.id.container, fragment, "$index")
            transcation.addToBackStack(null)
        }

        transcation.commit()
    }

    override fun onAdd(index: Int) {
        addFragment(index)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> super.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
