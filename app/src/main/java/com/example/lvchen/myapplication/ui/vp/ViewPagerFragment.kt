package com.example.lvchen.myapplication.ui.vp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lvchen.myapplication.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {
    lateinit var binding: FragmentViewPagerBinding

    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initView() {
        val arrayList = arrayListOf("视频样例", "配音样例")
        fragments.add(ViewPagerItemFragment())
        fragments.add(ViewPagerItemFragment())

        binding.tabLayout.setViewPager(
            binding.fragmentVp2,
            arrayList.toTypedArray(),
            requireActivity(),
            fragments
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        initView()
    }
}