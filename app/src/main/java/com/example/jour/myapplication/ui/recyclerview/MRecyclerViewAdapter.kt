package com.example.jour.myapplication.ui.recyclerview

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jour.myapplication.R


class MRecyclerViewAdapter : RecyclerView.Adapter<MRecyclerViewAdapter.MViewHolder>() {

    val TYPE_FOOTER = -1001
    val TYPE_NORMAL = -1000
    var mDataList: MutableList<RecyclerItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        if (viewType == TYPE_FOOTER) {
            return MViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_foot_loading, parent, false)
            )
        }
        return MViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        val item = mDataList[position]
        if (item.type != TYPE_FOOTER) {
            holder.itemView.findViewById<TextView>(R.id.item_tv).text = mDataList[position].name
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mDataList[position].type
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun addLoadItem() {
        if (mDataList.isNotEmpty()
            && mDataList[mDataList.size - 1].type == TYPE_FOOTER
        ) return
        val footItem = RecyclerItem()
        footItem.type = TYPE_FOOTER
        mDataList.add(footItem)
        Handler().post {
            notifyItemChanged(mDataList.size - 1)
        }

    }

    fun removeLoadItem() {
        if (mDataList.size == 0) return
        if (mDataList[mDataList.size - 1].type != TYPE_FOOTER) return
        mDataList.removeAt(mDataList.size - 1)
        Handler().post {
            notifyItemChanged(mDataList.size - 1)
        }
//        notifyItemChanged(mDataList.size - 1)
    }

    class MViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mItemView: View

        init {
            mItemView = itemView
        }
    }


}