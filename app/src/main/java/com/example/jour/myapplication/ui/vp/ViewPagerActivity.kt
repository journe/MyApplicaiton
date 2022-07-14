package com.example.jour.myapplication.ui.vp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jour.myapplication.databinding.ActivityViewPagerNestBinding

class ViewPagerActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewPagerNestBinding
    private val fragments: ArrayList<Fragment> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerNestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arrayList = arrayListOf("视频样例", "配音样例")

        fragments.add(ViewPagerFragment())
        fragments.add(ViewPagerFragment())

        binding.tabLayout
        binding.tabLayout.setViewPager(
            binding.activityVp2,
            arrayList.toTypedArray(),
            this,
            fragments
        )

        binding.activityVp2.offscreenPageLimit = arrayList.size
    }
}