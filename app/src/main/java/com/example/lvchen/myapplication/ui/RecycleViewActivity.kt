package com.example.lvchen.myapplication.ui

import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.lvchen.myapplication.R
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_recycle_view.recycleRootView
import kotlinx.android.synthetic.main.layout_main_treasure.view.ivTreasure
import kotlinx.android.synthetic.main.layout_main_treasure.view.llCountdown
import kotlinx.android.synthetic.main.layout_main_treasure.view.tvTips
import org.jetbrains.anko.dip

class RecycleViewActivity : AppCompatActivity() {

  private lateinit var mViewRecyclerView: RecyclerView

  private val proName = arrayOf(
      "名称0", "名称1", "名称2", "名称3", "名称4", "名称5",
      "名称6", "名称7", "名称8", "名称9", "名称10", "名称11", "名称12",
      "名称13", "名称14", "名称15", "名称16", "名称17", "名称18", "名称19"
  )
  private val treasureView by lazy {
    LayoutInflater.from(this)
        .inflate(R.layout.layout_main_treasure, recycleRootView, false)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recycle_view)
    mViewRecyclerView = findViewById(R.id.recycle_view)
    mViewRecyclerView.layoutManager = LinearLayoutManager(this)
    mViewRecyclerView.adapter = MyRecycleAdapter()
    mViewRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
      ) {
        super.onScrolled(recyclerView, dx, dy)
      }

      override fun onScrollStateChanged(
        recyclerView: RecyclerView,
        newState: Int
      ) {
        super.onScrollStateChanged(recyclerView, newState)
        when (newState) {
          RecyclerView.SCROLL_STATE_DRAGGING -> {
            treasureView.visibility = View.GONE
          }
          else -> {
            treasureView.visibility = View.VISIBLE
          }
        }
        //direction为 -1 表示手指向下滑动（屏幕向上滑动）， 1 表示手指向上滑动（屏幕向下滑动）
        if (!recyclerView.canScrollVertically(1) || !recyclerView.canScrollVertically(-1)) {
          treasureView.visibility = View.VISIBLE
        } else {

        }

      }
    })
//    Looper.myQueue()
//        .addIdleHandler { initTreasureView() }
  }

  override fun onResume() {
    super.onResume()
    initTreasureView()
  }

  private fun initTreasureView(): Boolean {
    val params = RelativeLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )
        .apply {
          addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1)
          addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1)
          bottomMargin = dip(40)
          rightMargin = dip(20)
        }

    Glide.with(this)
        .load("https://imgs-1253854453.image.myqcloud.com/d050a37a3bb3fefac530d5a473df88b9.gif")
        .into(treasureView.ivTreasure)
    treasureView.tvTips.visibility = View.VISIBLE
    treasureView.llCountdown.visibility = View.GONE
    treasureView.tvTips.text = "+25朵金花"
    recycleRootView.addView(treasureView, params)
    return true
  }

  internal inner class MyRecycleAdapter : RecyclerView.Adapter<MyRecycleAdapter.RecycleViewHolder>() {

    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): RecycleViewHolder {
      return RecycleViewHolder(
          LayoutInflater.from(this@RecycleViewActivity)
              .inflate(R.layout.view_pager_grid_view_item, null)
      )
    }

    override fun onBindViewHolder(
      holder: RecycleViewHolder,
      position: Int
    ) {
      holder.textView.text = proName[position] + "index" + position
      holder.imageView.setImageResource(R.mipmap.ic_launcher)
    }

    override fun getItemCount(): Int {
      return proName.size
    }

    internal inner class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      var imageView: ImageView = itemView.findViewById(R.id.grid_item_iv)
      var textView: TextView = itemView.findViewById(R.id.grid_item_tv)
    }
  }
}
