package com.example.jour.myapplication.ui.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jour.myapplication.R
import com.example.jour.myapplication.databinding.ActivityBannerBinding


class BannerActivity : AppCompatActivity() {
    lateinit var binding: ActivityBannerBinding

    private val imageList = listOf(
        R.drawable.head_default,
        R.drawable.default_album_art,
        R.drawable.customactivityoncrash_error_image,
        R.drawable.ic_notification_logo,
        R.drawable.ic_work_hard,
        R.drawable.radio_bg,
        R.drawable.bg_cata2_topic_list,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        useBanner()
    }

    private fun useBanner() {
        val vipStringList = listOf(
            "用户XXX  1分钟前成为终身会员",
            "用户XXX  2分钟前成为终身会员",
            "用户XXX  3分钟前成为终身会员",
            "用户XXX  4分钟前成为终身会员",
            "用户XXX  5分钟前成为终身会员",
        )
        //--------------------------简单使用-------------------------------
        binding.banner.scrollTime = 2000
        binding.banner.addBannerLifecycleObserver(this) //添加生命周期观察者
            .setAdapter(ImageAdapter(vipStringList))

        //—————————————————————————如果你想偷懒，而又只是图片轮播————————————————————————
//        binding.banner.setAdapter(object : BannerImageAdapter<DataBean?>(DataBean.getTestData3()) {
//            override fun onBindView(
//                holder: BannerImageHolder,
//                data: DataBean,
//                position: Int,
//                size: Int
//            ) {
//                //图片加载自己实现
//                Glide.with(holder.itemView)
//                    .load(data.imageUrl)
//                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
//                    .into(holder.imageView)
//            }
//        })
//            .addBannerLifecycleObserver(this) //添加生命周期观察者
//            .setIndicator(CircleIndicator(this))
    }
}