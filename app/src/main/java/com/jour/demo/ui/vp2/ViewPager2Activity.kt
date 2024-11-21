package com.jour.demo.ui.vp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jour.demo.databinding.ActivityViewPager2Binding
import com.jour.demo.ui.vp.ViewPagerFragment

class ViewPager2Activity : AppCompatActivity() {
    lateinit var binding: ActivityViewPager2Binding

    private val fragments: MutableList<Fragment> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPager2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val arrayList = arrayListOf("视频样例", "配音样例")

        fragments.add(ViewPager2Fragment())
        fragments.add(ViewPager2Fragment())

        binding.activityVp2.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle, fragments)
        binding.tabLayout.setViewPager2(binding.activityVp2, arrayList)
    }
}