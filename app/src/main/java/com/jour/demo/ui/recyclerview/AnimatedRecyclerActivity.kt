package com.jour.demo.ui.recyclerview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jour.demo.R
import com.jour.demo.databinding.ActivityAnimatedRecyclerBinding

class AnimatedRecyclerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimatedRecyclerBinding
    private val itemList = mutableListOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    private lateinit var adapter: AnimatedItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimatedRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化RecyclerView
        adapter = AnimatedItemAdapter(
            items = itemList,
            onDeleteClick = { position ->
                // 删除Item并触发动画
                removeItem(position)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AnimatedRecyclerActivity)
            adapter = this@AnimatedRecyclerActivity.adapter
            // 设置默认动画器（可选，会被自定义动画覆盖）
            itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator().apply {
                addDuration = 300
                removeDuration = 300
            }
        }

        // 添加Item按钮
        binding.addButton.setOnClickListener {
            val newItem = "Item ${itemList.size + 1}"
            addItem(0, newItem) // 在头部添加新Item
        }
    }

    // 添加Item并显示动画
    private fun addItem(position: Int, item: String) {
        itemList.add(position, item)
        // 通知适配器Item已添加
        adapter.notifyItemInserted(position)
        // 滚动到添加的位置
        binding.recyclerView.scrollToPosition(position)
    }

    // 删除Item并显示动画
    private fun removeItem(position: Int) {
        itemList.removeAt(position)
        // 通知适配器Item已删除
        adapter.notifyItemRemoved(position)
    }
}

// 自定义适配器
class AnimatedItemAdapter(
    private val items: MutableList<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<AnimatedItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.itemText)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        init {
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animated, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]

        // 为新添加的Item设置进入动画
        holder.itemView.alpha = 0f
        holder.itemView.translationY = 50f
        holder.itemView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(300)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }

    // 重写此方法以支持删除动画
    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        // 清除动画，避免复用问题
        holder.itemView.clearAnimation()
    }

    // 自定义删除动画
    fun animateRemove(holder: ViewHolder) {
        holder.itemView.animate()
            .alpha(0f)
            .translationX(holder.itemView.width.toFloat())
            .setDuration(300)
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // 动画结束后通知适配器
                    val position = holder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        notifyItemRemoved(position)
                    }
                }
            })
            .start()
    }

    override fun getItemCount() = items.size
}
