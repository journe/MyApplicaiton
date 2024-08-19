package com.jour.demo.ui.vp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jour.demo.R
import com.jour.demo.databinding.FragmentViewPager2Binding
import com.jour.demo.databinding.FragmentViewPager2ItemBinding
import com.jour.demo.databinding.FragmentViewPagerItemBinding
import com.jour.demo.ui.recyclerview.MRecyclerViewAdapter
import com.jour.demo.ui.recyclerview.RecyclerItem
import com.orhanobut.logger.Logger

class ViewPagerItemFragment : Fragment() {
    lateinit var binding: FragmentViewPagerItemBinding

    private var mDataList: MutableList<RecyclerItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerItemBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        val adapter = MRecyclerViewAdapter()
        adapter.mDataList = genData()
        binding.recycleView.adapter = adapter
    }

    private fun genData(): MutableList<RecyclerItem> {
        for (i in 0..15) {
            mDataList.add(RecyclerItem(name = "item$i"))
        }
        Logger.d("genData")
        return mDataList
    }
}