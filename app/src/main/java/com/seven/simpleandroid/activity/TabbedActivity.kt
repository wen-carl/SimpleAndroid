package com.seven.simpleandroid.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.design.widget.TabLayout.BaseOnTabSelectedListener
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import com.seven.simpleandroid.R
import kotlinx.android.synthetic.main.activity_tabbed.*
import kotlinx.android.synthetic.main.fragment_tabbed.view.*
import kotlinx.android.synthetic.main.item_multiple_tab.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TabbedActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        viewPager.adapter = mSectionsPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_tabbed, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        } else if (id == android.R.id.home) {
            super.onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return position.toString()
        }

        override fun getCount(): Int {
            return 3
        }
    }

    class PlaceholderFragment : Fragment(), BaseOnTabSelectedListener<TabLayout.Tab> {

        companion object {

            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }

        private lateinit var recyclerView : RecyclerView

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_tabbed, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            recyclerView = view.recycler_view
            //recyclerView.isNestedScrollingEnabled = false
            var w = recyclerView.width
            var h = recyclerView.height
            val wm = this.activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            val width = dm.widthPixels         // 屏幕宽度（像素）
            val height = dm.heightPixels       // 屏幕高度（像素）
            val density = dm.density         // 屏幕密度（0.75 / 1.0 / 1.5）
            val densityDpi = dm.densityDpi     // 屏幕密度dpi（120 / 160 / 240）
            // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
            val screenWidth = (width / density).toInt()  // 屏幕宽度(dp)
            val screenHeight = (height / density).toInt()// 屏幕高度(dp)

            val container = view.nestedScrollContainer
            recyclerView.layoutParams.height = height

            recyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = ImageRecyclerViewAdapter(getList(0))

            val tablayout = view.findViewById<TabLayout>(R.id.tabLayout2)
            tablayout.addOnTabSelectedListener(this)
        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            val adapter = recyclerView.adapter as ImageRecyclerViewAdapter
            adapter.data = getList(tab.position)
            adapter.notifyDataSetChanged()

            GlobalScope.launch(context = Dispatchers.Default, block = {
                delay(1000)
                var h1 = view?.nestedScrollView?.height
                var h2 = view?.nestedScrollContainer?.height
                var h3 = view?.recycler_view?.height
                Log.i("ImageAdapter", "nestedScrollView: $h1  nestedScrollContainer: $h2 recycler_view: $h3")
            })
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {

        }

        override fun onTabReselected(tab: TabLayout.Tab) {

        }

        private fun getList(position: Int): List<String> {
            val list = mutableListOf<String>()
            for (i in 0..50) {
                list.add("tab: $position  position: $i")
            }

            return list
        }
    }

    class ImageRecyclerViewAdapter(var data: List<String>) : RecyclerView.Adapter<ImageHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ImageHolder {
            val itemView = LayoutInflater.from(p0.context).inflate(R.layout.item_multiple_tab, p0, false)
            return ImageHolder(itemView)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: ImageHolder, position: Int) {
            val text = data[position]
            holder.tv.text = text
            val resourceId = if (position % 2 == 0) R.drawable.img_seven else R.drawable.img_seven2
            holder.img.setImageResource(resourceId)
            Log.i("ImageAdapter", "Position: $position text: $text")
        }
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.img
        var tv = itemView.tv_info
    }
}
