package com.seven.simpleandroid.activity

import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.seven.simpleandroid.R
import com.seven.simpleandroid.adapter.ImageAdapter
import com.seven.simpleandroid.interfaces.IOnItemClickListener
import kotlinx.android.synthetic.main.activity_shared_element.*

class SharedElementActivity : AppCompatActivity(), IOnItemClickListener<String, ImageAdapter.ImageHolder> {

    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_element)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvImage.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
        val adapter = ImageAdapter(images, this)
        rvImage.adapter = adapter

        setExitSharedElementCallback(object: SharedElementCallback() {
            override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                super.onMapSharedElements(names, sharedElements)

                if (-1 != index) {
                    val view = rvImage.layoutManager?.findViewByPosition(index)
                    val view1 = view?.findViewById<ImageView>(R.id.imageView)!!
                    sharedElements[names[0]] = view1
                    index = -1
                }
            }
        })
    }

    override fun onItemClicked(holder: ImageAdapter.ImageHolder, model: String) {
        val intent = Intent(this, SharedImageActivity::class.java)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, holder.imageView, "image")
        intent.putExtra(SharedImageActivity.INDEX, holder.adapterPosition)
        startActivityForResult(intent, REQUEST_CODE, options.toBundle())
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        postponeEnterTransition()

        index = data?.getIntExtra(SharedImageActivity.INDEX, -1)!!
        val manager = rvImage.layoutManager as androidx.recyclerview.widget.GridLayoutManager
        val first = manager.findFirstVisibleItemPosition()
        val last = manager.findLastVisibleItemPosition()
        manager.scrollToPosition(index)

        window.decorView.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                window.decorView.viewTreeObserver.removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return false;
            }
        })

        super.onActivityReenter(resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        val REQUEST_CODE = 1
        val images = listOf<String>(
            "https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg",
            "https://ww1.sinaimg.cn/large/0065oQSqgy1ftwcw4f4a5j30sg10j1g9.jpg",
            "https://ww1.sinaimg.cn/large/0065oQSqly1ftu6gl83ewj30k80tites.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqgy1ftt7g8ntdyj30j60op7dq.jpg",
            "https://ww1.sinaimg.cn/large/0065oQSqgy1ftrrvwjqikj30go0rtn2i.jpg",
            "https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg",
            "https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg",
            "http://ww1.sinaimg.cn/large/0073sXn7ly1ft82s05kpaj30j50pjq9v.jpg",
            "https://ww1.sinaimg.cn/large/0065oQSqly1ft5q7ys128j30sg10gnk5.jpg",
            "https://ww1.sinaimg.cn/large/0065oQSqgy1ft4kqrmb9bj30sg10fdzq.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1ft3fna1ef9j30s210skgd.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsysqszneoj30hi0pvqb7.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fswhaqvnobj30sg14hka0.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsvb1xduvaj30u013175p.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsq9iq8ttrj30k80q9wi4.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsp4iok6o4j30j60optbl.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsoe3k2gkkj30g50niwla.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsmis4zbe7j30sg16fq9o.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1frslruxdr1j30j60ok79c.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1k9cb5j30sg0y7q61.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1ykabxj30k00pracv.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsfq2pwt72j30qo0yg78u.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fsb0lh7vl0j30go0ligni.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fs8tym1e8ej30j60ouwhz.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fs8u1joq6fj30j60orwin.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fs7l8ijitfj30jg0shdkc.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fs35026dloj30j60ov79x.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fs34w0jx9jj30j60ootcn.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fs1vq7vlsoj30k80q2ae5.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1frjd77dt8zj30k80q2aga.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1frmuto5qlzj30ia0notd8.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1frqscr5o00j30k80qzafc.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1frrifts8l5j30j60ojq6u.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1frslibvijrj30k80q678q.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1frsllc19gfj30k80tfah5.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1frv03m8ky5j30iz0rltfp.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fryyn63fm1j30sg0yagt2.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1fs02a9b0nvj30sg10vk4z.jpg",
            "http://ww1.sinaimg.cn/large/0065oQSqly1frv032vod8j30k80q6gsz.jpg"
        )
    }
}
