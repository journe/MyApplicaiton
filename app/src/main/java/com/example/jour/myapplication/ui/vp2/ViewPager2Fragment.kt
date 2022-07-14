package com.example.jour.myapplication.ui.vp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jour.myapplication.databinding.FragmentViewPager2Binding
import com.example.jour.myapplication.ui.vp.ViewPagerItemFragment

class ViewPager2Fragment : Fragment() {
    lateinit var binding: FragmentViewPager2Binding

    private val fragments: MutableList<Fragment> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPager2Binding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initView() {
        val arrayList = arrayListOf("视频样例", "配音样例")
        fragments.add(ViewPagerItemFragment())
        fragments.add(ViewPagerItemFragment())

        binding.fragmentVp2.adapter = ViewPager2Adapter(childFragmentManager, lifecycle, fragments)
        binding.tabLayout.setViewPager2(binding.fragmentVp2, arrayList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        view.parent
    }
}