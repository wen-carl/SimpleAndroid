package com.seven.simpleandroid.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.seven.simpleandroid.R
import com.seven.simpleandroid.imageloader.GlideImageLoader
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_banner.*

class BannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        banner_pager.setImageLoader(GlideImageLoader())
            .setImages(listOf("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1530602940&di=52bc9263040997e5aa9aaa9f6e24b9d6&src=http://pic1.qiyipic.com/image/20160624/72/6f/li_260076_li_601.jpg", "https://timgsa.baidu.com/timg?image&quality=100&size=b9999_10000&sec=1530613027321&di=5364ff145d16913870ca6d87c1414579&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F86%2F97%2F01300543922769147919976468018.jpg", "https://timgsa.baidu.com/timg?image&quality=100&size=b9999_10000&sec=1530613027320&di=eb0590edecc9e9547b53a8108a863a20&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201607%2F23%2F20160723192348_s2JaW.png", "https://timgsa.baidu.com/timg?image&quality=100&size=b9999_10000&sec=1530613027320&di=30a7321eea0b8c69b80ce8ecd292cfa0&imgtype=0&src=http%3A%2F%2Fwx4.sinaimg.cn%2Forj360%2F6a629d37ly1fashdc8iltj20hs0pa40t0.jpg", "https://timgsa.baidu.com/timg?image&quality=100&size=b9999_10000&sec=1530613027316&di=cc91c036c707fcaf1daf89d6c20bd87c&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201608%2F06%2F20160806200539_cjenk.jpeg"))
            .setBannerTitles(listOf("A", "B", "C", "D", "E"))
            .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            .setDelayTime(2000)
            .start()
    }
}
