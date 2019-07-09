package com.seven.simpleandroid.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.BottomNavFragmentAdapter
import kotlinx.android.synthetic.main.activity_bottom_nav.*


class BottomNavActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //navigation.enableShiftMode(false)
        //navigation.isItemHorizontalTranslationEnabled = false
        navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        val badge = navigation.showBadge(R.id.navigation_share)
        badge.number = 100
        //badge.badgeTextColor = 0xFF0000
        navigation.setOnNavigationItemSelectedListener(this)

        viewpager.adapter = BottomNavFragmentAdapter(supportFragmentManager, listOf("Home", "Dashboard", "Notifications", "Share", "Share1"))
        viewpager.addOnPageChangeListener(this)
        viewpager.setPageTransformer(true, DepthPageTransformer())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> super.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val position = when (item.itemId) {
            R.id.navigation_home -> 0
            R.id.navigation_dashboard -> 1
            R.id.navigation_notifications -> 2
            R.id.navigation_share -> 3
            R.id.navigation_share1 -> 4
            else -> 0
        }

        viewpager.currentItem = position
        return true
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        navigation.selectedItemId = when (position) {
            0 -> R.id.navigation_home
            1 -> R.id.navigation_dashboard
            2 -> R.id.navigation_notifications
            3 -> R.id.navigation_share
            else -> R.id.navigation_home
        }
    }
}

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class ZoomOutPageTransformer : androidx.viewpager.widget.ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height

        when {
            position < -1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f
            position <= 1 -> { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.translationX = horzMargin - vertMargin / 2
                } else {
                    view.translationX = -horzMargin + vertMargin / 2
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                // Fade the page relative to its size.
                view.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)

            }
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 0f
        }
    }
}

class DepthPageTransformer : androidx.viewpager.widget.ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width

        when {
            position < -1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f
            position <= 0 -> { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.alpha = 1f
                view.translationX = 0f
                view.scaleX = 1f
                view.scaleY = 1f

            }
            position <= 1 -> { // (0,1]
                // Fade the page out.
                view.alpha = 1 - position

                // Counteract the default slide transition
                view.translationX = pageWidth * -position

                // Scale the page down (between MIN_SCALE and 1)
                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

            }
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 0f
        }
    }

    companion object {
        private const val MIN_SCALE = 0.75f
    }
}
