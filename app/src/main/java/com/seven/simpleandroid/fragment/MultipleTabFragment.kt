package com.seven.simpleandroid.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.MultipleTabAdapter
import com.seven.simpleandroid.model.ImgModel
import com.seven.simpleandroid.model.ImgSourceType
import kotlinx.android.synthetic.main.fragment_multiple_tab.*

private const val ARG_PARAM = "param"

class MultipleTabFragment : Fragment() {
    private val TAG = MultipleTabFragment::class.java.simpleName

    private var param: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")
        arguments?.let {
            param = it.getString(ARG_PARAM)
        }
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        log("onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        log("onDetach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("onDestroyView")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("onCreateView")
        return inflater.inflate(R.layout.fragment_multiple_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("onViewCreated")

        val data = mutableListOf<ImgModel>()
        for (i in 0..50) {
            val count = i % 3
            val model = when (count) {
                0 -> {
                    ImgModel(param!!, ImgSourceType.Heap, id = R.drawable.img_seven2)
                }
                1 -> {
                    ImgModel(param!!, ImgSourceType.Disk, url = "img_seven.jpg")
                }
                else -> {
                    ImgModel(param!!, ImgSourceType.Net, url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530532773224&di=4d312be545306e9803e434020fca94a2&imgtype=0&src=http%3A%2F%2Fpic1.qiyipic.com%2Fimage%2F20160624%2F72%2F6f%2Fli_260076_li_601.jpg")
                }
            }
            data.add(model)
        }

        recycler_view.adapter = MultipleTabAdapter(data)
        recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    private fun log(msg: String) {
        Log.i(TAG, "$param  $msg")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MultipleTabFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param1)
                }
            }
    }
}
