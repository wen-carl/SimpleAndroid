package com.seven.simpleandroid.activity

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.seven.simpleandroid.R
import com.seven.simpleandroid.model.ImgSourceType
import kotlinx.android.synthetic.main.activity_shared_image.*
import java.io.File

class SharedImageActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_TYPE = "type"
        const val IMAGE_URL = "url"
        const val IMAGE_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_image)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val value = intent.getIntExtra(IMAGE_TYPE, -1)
        val glide = Glide.with(this)
        when (value) {
            ImgSourceType.Net.value -> {
                val url = intent.getStringExtra(IMAGE_URL)
                glide.load(url)
                    .into(iv)
            }
            ImgSourceType.Heap.value -> {
                val id = intent.getIntExtra(IMAGE_ID, -1)
                if (-1 != id) {
                    glide.load(id)
                        .into(iv)
                } else {
                    toast("id error")
                }
            }
            ImgSourceType.Disk.value -> {
                val url = intent.getStringExtra(IMAGE_URL)
                glide.load(File(getExternalFilesDir(null), url))
                    .into(iv)
            }
            else -> {
                toast("type error")
            }
        }

        ViewCompat.setTransitionName(iv, "image")
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
