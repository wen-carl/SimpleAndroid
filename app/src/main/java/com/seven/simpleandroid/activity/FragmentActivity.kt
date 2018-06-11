package com.seven.simpleandroid.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        val fragment = PlusOneFragment.newInstance(index + 1)
        transcation.replace(R.id.fragment_plus, fragment, "${index}")
        transcation.addToBackStack(null)
        transcation.commit()
    }

    override fun onAdd(index: Int) {
        addFragment(index)
    }
}
