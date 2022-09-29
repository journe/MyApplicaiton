package com.jour.myapplication.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jour.myapplication.R
import com.jour.myapplication.databinding.ActivityRecycleViewBinding
import com.jour.myapplication.databinding.LayoutMainTreasureBinding
import com.orhanobut.logger.Logger
import org.jetbrains.anko.dip

class RecycleViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecycleViewBinding
    private val proName = arrayOf(
        "名称0", "名称1", "名称2", "名称3", "名称4", "名称5",
        "名称6", "名称7", "名称8", "名称9", "名称10", "名称11", "名称12",
        "名称13", "名称14", "名称15", "名称16", "名称17", "名称18", "名称19"
    )
//    private val treasureView by lazy {
//        LayoutInflater.from(this)
//            .inflate(R.layout.layout_main_treasure, recycleRootView, false)
//    }

    lateinit var treasureView: LayoutMainTreasureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        treasureView =
            LayoutMainTreasureBinding.inflate(layoutInflater, binding.recycleRootView, false)
        binding.recycleView.apply {
            adapter = MyRecycleAdapter()
            addOnScrollListener(object :
                androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: androidx.recyclerview.widget.RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    super.onScrolled(recyclerView, dx, dy)
                }

                override fun onScrollStateChanged(
                    recyclerView: androidx.recyclerview.widget.RecyclerView,
                    newState: Int
                ) {
                    super.onScrollStateChanged(recyclerView, newState)
                    Logger.d(newState)
                    when (newState) {
                        androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING -> {
//            treasureView.visibility = View.GONE
                            treasureView.rootView.animate().translationX(250f)
//                .interpolator = AccelerateInterpolator()
                        }
                        else -> {
//            treasureView.visibility = View.VISIBLE
                            treasureView.rootView.animate().translationX(-25f)
//                .interpolator = DecelerateInterpolator()
                        }
                    }
                    //direction为 -1 表示手指向下滑动（屏幕向上滑动）， 1 表示手指向上滑动（屏幕向下滑动）
//        if (!recyclerView.canScrollVertically(1) || !recyclerView.canScrollVertically(-1)) {
//          treasureView.visibility = View.VISIBLE
//        } else {
//
//        }

                }
            })

        }

//    Looper.myQueue()
//        .addIdleHandler { initTreasureView() }

        val snapHelper = object : androidx.recyclerview.widget.PagerSnapHelper() {
            override fun findTargetSnapPosition(
                layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager?,
                velocityX: Int,
                velocityY: Int
            ): Int {
                val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)

//        if (position >= 0 && position < feedVideoAdapter.data.size) {
//          feedVideoAdapter.data[position]?.let {
//            switchVideo(it, position)
//          }
//        }

                return position
            }
        }
        snapHelper.attachToRecyclerView(binding.recycleView)
    }

    override fun onResume() {
        super.onResume()
        initTreasureView()
    }

    @SuppressLint("ObjectAnimatorBinding")
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
        binding.recycleRootView.addView(treasureView.rootView, params)

//    val transition  = LayoutTransition()
//    recycleRootView.layoutTransition = transition
//
//    //使用翻转进入的动画代替默认动画
//    val appearAnim = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f)
//        .setDuration(transition.getDuration(LayoutTransition.APPEARING))
//    transition.setAnimator(LayoutTransition.APPEARING, appearAnim)
//
//    //使用翻转消失的动画代替默认动画
//    val disappearAnim = ObjectAnimator.ofFloat(null, "rotationX", 0f,
//    90f).setDuration(
//    transition.getDuration(LayoutTransition.DISAPPEARING))
//    transition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim)

        //使用滑动动画代替默认布局改变的动画
        //这个动画会让视图滑动进入并短暂地缩小一半，具有平滑和缩放的效果
//    val pvhSlide = PropertyValuesHolder.ofFloat("y", 0f, 1f)
//    val pvhScaleY = PropertyValuesHolder.ofFloat("scaleY",
//    1f, 0.5f, 1f)
//    val pvhScaleX = PropertyValuesHolder.ofFloat("scaleX",
//    1f, 0.5f, 1f)
//
//    //这里将上面三个动画综合
//    val changingDisappearAnim = ObjectAnimator.ofPropertyValuesHolder(
//        this, pvhSlide, pvhScaleY, pvhScaleX)
//    changingDisappearAnim.duration = transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING)
//    transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
//        changingDisappearAnim)
        return true
    }

    internal inner class MyRecycleAdapter :
        androidx.recyclerview.widget.RecyclerView.Adapter<MyRecycleAdapter.RecycleViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecycleViewHolder {
            return RecycleViewHolder(
                LayoutInflater.from(this@RecycleViewActivity)
                    .inflate(R.layout.view_recycle_view_item, null)
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

        internal inner class RecycleViewHolder(itemView: View) :
            androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
            var imageView: ImageView = itemView.findViewById(R.id.grid_item_iv)
            var textView: TextView = itemView.findViewById(R.id.grid_item_tv)
        }
    }
}
