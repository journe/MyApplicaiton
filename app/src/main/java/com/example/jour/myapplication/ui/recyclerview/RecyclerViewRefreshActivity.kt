package com.example.jour.myapplication.ui.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jour.myapplication.databinding.ActivityRecyclerViewRefreshBinding
import com.example.jour.myapplication.ui.RecycleViewActivity
import com.orhanobut.logger.Logger

class RecyclerViewRefreshActivity : AppCompatActivity() {
    private var isLoadingNext: Boolean = false
    private lateinit var binding: ActivityRecyclerViewRefreshBinding
    private val mHandler: Handler = Handler()
    private val adapter = MRecyclerViewAdapter()
    private var mDataList: MutableList<RecyclerItem> = ArrayList()

    private val runnable = Runnable {
        pullData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewRefreshBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        adapter.mDataList = genData()
        initView()
    }

    private fun initView() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
        binding.recycleView.adapter = adapter
        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = lm.findLastVisibleItemPosition()
                val totalCount = lm.itemCount
                if (lastPosition == totalCount - 1 && !binding.swipeRefresh.isRefreshing && !isLoadingNext) {
                    if (totalCount > 0) {
                        adapter.addLoadItem()
                    }
                    isLoadingNext = true

                    mHandler.postDelayed(runnable, 1000)
                }
            }
        })
    }


    private fun pullData() {
        adapter.removeLoadItem()
        val old = mDataList.size
        genData()
        Logger.d(mDataList.size)
        adapter.notifyItemRangeChanged(old, mDataList.size - old)
        isLoadingNext = false

    }

    private fun genData(): MutableList<RecyclerItem> {
        for (i in 0..15) {
            mDataList.add(RecyclerItem(name = "item$i"))
        }
        return mDataList
    }
}