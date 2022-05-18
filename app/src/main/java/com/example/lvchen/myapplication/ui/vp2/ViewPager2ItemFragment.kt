package com.example.lvchen.myapplication.ui.vp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lvchen.myapplication.R
import com.example.lvchen.myapplication.databinding.FragmentViewPager2Binding
import com.example.lvchen.myapplication.databinding.FragmentViewPager2ItemBinding
import com.example.lvchen.myapplication.ui.recyclerview.MRecyclerViewAdapter
import com.example.lvchen.myapplication.ui.recyclerview.RecyclerItem

class ViewPager2ItemFragment : Fragment() {
    lateinit var binding: FragmentViewPager2ItemBinding

    private var mDataList: MutableList<RecyclerItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPager2ItemBinding.inflate(inflater, container, false)
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
        return mDataList
    }
}