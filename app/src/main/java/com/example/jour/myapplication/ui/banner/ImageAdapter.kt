package com.example.jour.myapplication.ui.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jour.myapplication.databinding.ItemVipRecordViewpagerBinding
import com.youth.banner.adapter.BannerAdapter


/**
 * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
class ImageAdapter(mData: List<String>) :
    BannerAdapter<String, ImageAdapter.ViewHolder>(mData) {
    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVipRecordViewpagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindView(
        holder: ViewHolder?, data: String?, position: Int, size: Int
    ) {
        holder?.bind(data)
    }


    inner class ViewHolder(private val binding: ItemVipRecordViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bean: String?) {
            binding.content.text = bean
        }
    }
}
