package com.jour.demo.ui.coordinator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jour.demo.bean.WaterFallItemData
import com.jour.demo.databinding.ActivityCoordinatorBinding
import com.jour.demo.databinding.ListItemVideoBinding

//import com.zhy.adapter.recyclerview.CommonAdapter
//import com.zhy.adapter.recyclerview.base.ViewHolder

class CoordinatorActivity : AppCompatActivity() {
	lateinit var binding: ActivityCoordinatorBinding

	private val gifList = arrayListOf(
		"https://cdn.wuhandmj.cn/landing-h5/alipay-landing-style3/member-list-20240827.png",
		"https://cdn.wuhandmj.cn/landing-h5/alipay-landing-style3/coupon-20240827.png",
		"https://cdn.wuhandmj.cn/landing-h5/alipay-landing-style4/hulu-20240829.png",
	)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoordinatorBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val items = arrayListOf<WaterFallItemData>()
		gifList.forEach {
			items.add(WaterFallItemData(topicVideo = it))
		}
		binding.recycleView.adapter = CoorAdapter(items)

	}


	inner class CoorAdapter(private val data: List<WaterFallItemData>) :
		RecyclerView.Adapter<CoorAdapter.ViewHolder>() {
		override fun onCreateViewHolder(
			parent: ViewGroup,
			viewType: Int
		): ViewHolder {
			return ViewHolder(
				ListItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			)
		}

		override fun onBindViewHolder(
			holder: ViewHolder,
			position: Int
		) {
			holder.bind(data[position])
		}

		override fun getItemCount(): Int {
			return data.size
		}

		inner class ViewHolder(private val binding: ListItemVideoBinding) :
			RecyclerView.ViewHolder(binding.root) {
			fun bind(bean: WaterFallItemData) {
				binding.topicImage.load(bean.topicVideo)
			}
		}
	}

}