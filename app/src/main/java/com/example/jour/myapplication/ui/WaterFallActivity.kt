package com.example.jour.myapplication.ui

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.example.jour.myapplication.R
import com.example.jour.myapplication.bean.WaterFallItemData
import com.orhanobut.logger.Logger
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.activity_water_fall.water_recycle_view
import org.jetbrains.anko.dip
import org.jetbrains.anko.find

class WaterFallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_fall)
        val gifList = arrayListOf(
            "https://static-cdn.xiangwushuo.com/bmgqv8rtfhp4tsev5dq0.mp4.gif",
            "https://static-cdn.xiangwushuo.com/bmgqv6rtfhp4tsev5dn0.mp4.gif",
            "https://static-cdn.xiangwushuo.com/bmgqtm3tfhp4tsev5cdg.mp4.gif",
            "https://static-cdn.xiangwushuo.com/bmgqrvjtfhp4tsev5ah0.mp4.gif",
            "https://static-cdn.xiangwushuo.com/bmgqsebtfhp4tsev5bcg.mp4.gif",
            "https://static-cdn.xiangwushuo.com/bmgqrsjtfhp4tsev5ab0.mp4.gif",
            "https://static-cdn.xiangwushuo.com/bmgqrqrtfhp4tsev5a9g.mp4.gif",
            "https://static-cdn.xiangwushuo.com/bmgqv1jtfhp4tsev5dig.mp4.gif"
        )
        val items = arrayListOf<WaterFallItemData>()
        gifList.forEach {
            items.add(WaterFallItemData(topicVideo = it))
        }
        water_recycle_view.adapter = getDataItemAdapter(items)
        water_recycle_view.addItemDecoration(object :
            androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: androidx.recyclerview.widget.RecyclerView,
                state: State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = dip(8)
                outRect.left = dip(8)
                outRect.right = dip(8)
                outRect.bottom = dip(8)
            }
        })
        water_recycle_view.addOnScrollListener(object :
            androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
//        when (newState) {
//          RecyclerView.SCROLL_STATE_IDLE -> {
//            val ids = getVisibleItemsId()
//            ids.forEach {
//              val drawable = getItemGifDrawable(it)
//              drawable.stop()
//              drawable.clearAnimationCallbacks()
//            }
//            playItemGifByOrder(ids, 0)
//          }
//          RecyclerView.SCROLL_STATE_DRAGGING -> {
//            getVisibleItemsId().forEach {
//              val drawable = getItemGifDrawable(it)
//              drawable.start()
//              drawable.clearAnimationCallbacks()
//            }
//          }
//          else -> {
//          }
//        }
            }
        })
    }

    fun getVisibleItemsId(): ArrayList<Int> {
        val lm =
            water_recycle_view.layoutManager as androidx.recyclerview.widget.StaggeredGridLayoutManager
        val startId = lm.findFirstVisibleItemPositions(null)[0]
        val lastIds = lm.findLastVisibleItemPositions(null)
        val lastId = lastIds[lastIds.size - 1]
        val visibleIds = arrayListOf<Int>()
        if (startId != lastId) {
            for (i in startId..lastId) {
                visibleIds.add(i)
            }
        }
        return visibleIds
    }

    private fun getItemGifDrawable(position: Int): GifDrawable {
        val itemView = water_recycle_view.findViewHolderForLayoutPosition(position)
            ?.itemView
        return itemView?.find<ImageView>(
            R.id.topic_image
        )?.drawable as GifDrawable
    }

    fun playItemGifByOrder(
        ids: ArrayList<Int>,
        i: Int
    ) {
        var index = i
        Logger.d(index)
        val drawable = getItemGifDrawable(ids[index])
        drawable.setLoopCount(1)
        drawable.start()
        drawable.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                super.onAnimationEnd(drawable)
                index++
                if (index <= ids.size - 1) {
                    playItemGifByOrder(ids, index)
                } else {
                    return
                }
            }
        })

    }

    private fun getDataItemAdapter(cataItems: ArrayList<WaterFallItemData>): CommonAdapter<WaterFallItemData> {
        return object : CommonAdapter<WaterFallItemData>(
            this, R.layout.list_item_video, cataItems
        ) {
            override fun convert(
                holder: ViewHolder?,
                itemData: WaterFallItemData?,
                position: Int
            ) {
                val sharedImage = holder!!.getView<ImageView>(R.id.topic_image)

                Glide.with(this@WaterFallActivity)
                    .load(itemData?.topicVideo)
                    .into(holder!!.getView(R.id.topic_image))

                holder.convertView.setOnClickListener {
                    val intent = Intent(this@WaterFallActivity, WaterFallDetailActivity::class.java)
                    intent.putExtra("url", itemData!!.topicVideo)
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        this@WaterFallActivity, sharedImage,
                        "topicVideo"
                    )
                    startActivity(intent, options.toBundle())
                }

            }
        }
    }
}
