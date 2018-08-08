package com.seven.simpleandroid.activity

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Images.Thumbnails.IMAGE_ID
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView.HitTestResult.IMAGE_TYPE
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.seven.simpleandroid.R
import com.seven.simpleandroid.model.ImgSourceType
import kotlinx.android.synthetic.main.activity_shared_image.*
import kotlinx.android.synthetic.main.item_image_pager.*
import java.io.File

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
        imagePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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

        ViewCompat.setTransitionName(imagePager, "image")
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

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent()
        intent.putExtra(INDEX, index)
        setResult(SharedElementActivity.REQUEST_CODE, intent)
    }

    class ImagePagerAdapter(val images: List<String>) : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return images.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context).inflate(R.layout.item_image_pager, container, false)
            container.addView(view)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            //ViewCompat.setTransitionName(imageView, "image")
            Glide.with(view)
                .load(images[position])
                .into(imageView)

            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}
