package com.example.jour.myapplication.ui.vp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jour.myapplication.R
import com.example.jour.myapplication.databinding.FragmentViewPager2Binding
import com.example.jour.myapplication.databinding.FragmentViewPager2ItemBinding
import com.example.jour.myapplication.databinding.FragmentViewPagerItemBinding
import com.example.jour.myapplication.ui.recyclerview.MRecyclerViewAdapter
import com.example.jour.myapplication.ui.recyclerview.RecyclerItem
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