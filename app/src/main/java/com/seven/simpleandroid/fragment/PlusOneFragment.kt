package com.seven.simpleandroid.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seven.simpleandroid.R
import com.seven.simpleandroid.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_plus_one.*


private const val INDEX = "index"

class PlusOneFragment : Fragment() {
    private var index: Int = 0

    private var mListener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }

        // TODO
        if (arguments != null) {
            index = arguments!!.getInt(INDEX)
        }
        log()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            index = arguments!!.getInt(INDEX)
        }
        log()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log()
        return inflater.inflate(R.layout.fragment_plus_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log()

        btn_plus.text = "$index"
        btn_plus.setOnClickListener {
            mListener?.onAdd(index)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        log()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        log()
    }

    override fun onStart() {
        super.onStart()
        log()
    }

    override fun onResume() {
        super.onResume()
        log()
    }

    override fun onPause() {
        super.onPause()
        log()
    }

    override fun onStop() {
        super.onStop()
        log()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log()
    }

    override fun onDestroy() {
        super.onDestroy()
        log()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        log()
    }

    private fun log() {
        LogUtils.logMethodName(TAG, "Fragment $index --> ")
    }

    interface OnFragmentInteractionListener {
        fun onAdd(index: Int)
    }

    companion object {

        private val TAG = "PlusOneFragment"

        fun newInstance(index: Int): PlusOneFragment {
            val fragment = PlusOneFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}

