package com.seven.simpleandroid.activity

import android.app.Activity
import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.seven.simpleandroid.R
import kotlinx.android.synthetic.main.activity_shared_image.*
import kotlinx.android.synthetic.main.item_image_pager.view.*

class SharedImageActivity : AppCompatActivity() {

    companion object {
        const val INDEX = "index"
    }

    private var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_image)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imagePager.adapter = ImagePagerAdapter(SharedElementActivity.images)
        imagePager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                index = position
            }
        })

        index = intent.getIntExtra(INDEX, -1)
        if (-1 == index) {
            toast("error")
        } else {
            imagePager.currentItem = index
        }

        //ViewCompat.setTransitionName(imagePager, "image")

        setEnterSharedElementCallback(object: SharedElementCallback() {
            override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                super.onMapSharedElements(names, sharedElements)

                val card = imagePager.rootView.findViewWithTag<androidx.cardview.widget.CardView>(index)
                sharedElements[names[0]] = card.imageView
            }
        })
    }

    override fun finishAfterTransition() {
        val intent = Intent()
        intent.putExtra(INDEX, index)
        setResult(Activity.RESULT_OK, intent)

        super.finishAfterTransition()
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class ImagePagerAdapter(val images: List<String>) : androidx.viewpager.widget.PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return images.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context).inflate(R.layout.item_image_pager, container, false)
            container.addView(view)

            val imageView = view.imageView
            Glide.with(view)
                .load(images[position])
                .into(imageView)

            view.tag = position

            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}
