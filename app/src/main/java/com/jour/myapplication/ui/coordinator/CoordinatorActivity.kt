package com.jour.myapplication.ui.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jour.myapplication.R
import com.jour.myapplication.bean.WaterFallItemData
import com.jour.myapplication.databinding.ActivityCoordinatorBinding
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

class CoordinatorActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoordinatorBinding

    private val gifList = arrayListOf(
        "https://static-cdn.xiangwushuo.com/bmgqv8rtfhp4tsev5dq0.mp4.gif",
        "https://static-cdn.xiangwushuo.com/bmgqv6rtfhp4tsev5dn0.mp4.gif",
        "https://static-cdn.xiangwushuo.com/bmgqtm3tfhp4tsev5cdg.mp4.gif",
        "https://static-cdn.xiangwushuo.com/bmgqrvjtfhp4tsev5ah0.mp4.gif",
        "https://static-cdn.xiangwushuo.com/bmgqsebtfhp4tsev5bcg.mp4.gif",
        "https://static-cdn.xiangwushuo.com/bmgqrsjtfhp4tsev5ab0.mp4.gif",
        "https://static-cdn.xiangwushuo.com/bmgqrqrtfhp4tsev5a9g.mp4.gif",
        "https://static-cdn.xiangwushuo.com/bmgqv1jtfhp4tsev5dig.mp4.gif"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoordinatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = arrayListOf<WaterFallItemData>()
        gifList.forEach {
            items.add(WaterFallItemData(topicVideo = it))
        }
        binding.recycleView.adapter = getDataItemAdapter(items)

    }

    private fun getDataItemAdapter(cataItems: ArrayList<WaterFallItemData>): CommonAdapter<WaterFallItemData> {
        return object : CommonAdapter<WaterFallItemData>(
            this, R.layout.list_item_video, cataItems
        ) {
            override fun convert(
                holder: ViewHolder?,
                itemData: WaterFallItemData?,
                position: Int
            ) {

                Glide.with(this@CoordinatorActivity)
                    .load(itemData?.topicVideo)
                    .into(holder!!.getView(R.id.topic_image))

            }
        }
    }

}