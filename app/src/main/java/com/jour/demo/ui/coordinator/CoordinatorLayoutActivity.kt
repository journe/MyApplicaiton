package com.jour.demo.ui.coordinator

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jour.demo.databinding.ActivityScrollingBinding
import com.jour.demo.ui.vp.ViewPagerItemFragment
import com.jour.demo.ui.vp2.ViewPager2Adapter

class CoordinatorLayoutActivity : AppCompatActivity() {

	private val fragments = listOf(
		ViewPagerItemFragment(),
		ViewPagerItemFragment(),
	)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val binding = ActivityScrollingBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.storyDetailToolbar)
		binding.storyDetailFab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
				.setAction("Action", null)
				.show()
		}

		binding.storyDetailToolbarLayout.apply {
			setCollapsedTitleTextColor(Color.BLACK)
			setExpandedTitleColor(Color.BLUE)
			title = "CoordinatorLayoutActivity"
		}

		val arrayList = arrayListOf("视频样例", "配音样例")

		binding.storyDetailViewpager.adapter =
			ViewPager2Adapter(supportFragmentManager, lifecycle, fragments)
		binding.tabLayout.setViewPager2(binding.storyDetailViewpager, arrayList)
	}
}
