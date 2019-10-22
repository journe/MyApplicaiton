package com.example.lvchen.myapplication.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.lvchen.myapplication.R
import com.example.lvchen.myapplication.bean.CataBean
import com.example.lvchen.myapplication.bean.CataItem
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.activity_catalogue.*

class CatalogueActivity : AppCompatActivity() {

  val cataBeans = arrayListOf<CataBean>()

  init {

    cataBeans.add(
        CataBean(
            name = "健康养生", itemIcon = R.mipmap.ic_launcher_round, cataList = arrayListOf(
            CataItem(itemName = "养生课堂", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "减肥1", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "减肥2", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "减肥3", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "减肥4", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "减肥5", itemIcon = R.mipmap.ic_launcher_round)
        )
        )
    )
    cataBeans.add(
        CataBean(
            name = "穿搭百科", itemIcon = R.mipmap.ic_launcher_round, cataList = arrayListOf(
            CataItem(itemName = "穿搭百科1", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科2", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科3", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科4", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科5", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科6", itemIcon = R.mipmap.ic_launcher_round)
        )
        )
    )
    cataBeans.add(
        CataBean(
            name = "美食相关", itemIcon = R.mipmap.ic_launcher_round, cataList = arrayListOf(
            CataItem(itemName = "美食相关1", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关2", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关3", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关4", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关5", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关6", itemIcon = R.mipmap.ic_launcher_round)
        )
        )
    )
    cataBeans.add(
        CataBean(
            name = "美妆个护", itemIcon = R.mipmap.ic_launcher_round, cataList = arrayListOf(
            CataItem(itemName = "美妆个护1", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "美妆个护2", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "美妆个护3", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "美妆个护4", itemIcon = R.mipmap.ic_launcher_round),
            CataItem(itemName = "美妆个护5", itemIcon = R.mipmap.ic_launcher_round)
        )
        )
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_catalogue)

    catalogue_rv.adapter = getCataAdapter(cataBeans)
    catalogue_item_rv.adapter = getCataItemAdapter(cataBeans[0].cataList)
  }

  private fun changeCataItem(position: Int) {
    catalogue_item_rv.adapter = getCataItemAdapter(cataBeans[position].cataList)
  }

  private fun getCataAdapter(cataBeans: ArrayList<CataBean>): CommonAdapter<CataBean> {
    return object : CommonAdapter<CataBean>(this, R.layout.item_catalogue, cataBeans) {
      var lastPosition = 0
      override fun convert(
        holder: ViewHolder?,
        t: CataBean?,
        position: Int
      ) {
        holder?.setText(R.id.catalogue_tv, t?.name)
        holder?.setOnClickListener(
            R.id.linearLayout
        ) { v ->
          if (position != lastPosition) {
            v.isSelected = true
            notifyDataSetChanged()
            changeCataItem(position)
          }
          lastPosition = position
        }
        holder?.getView<View>(R.id.linearLayout)!!.isSelected = lastPosition == position
        holder?.getView<View>(R.id.catalogue_indicator)
            .visibility = if (holder?.getView<View>(R.id.linearLayout)!!.isSelected) {
          View.VISIBLE
        } else {
          View.GONE
        }
      }
    }
  }

  private fun getCataItemAdapter(cataItems: ArrayList<CataItem>): CommonAdapter<CataItem> {
    return object : CommonAdapter<CataItem>(this, R.layout.item_catalogue_grid, cataItems) {
      override fun convert(
        holder: ViewHolder?,
        t: CataItem?,
        position: Int
      ) {
        holder?.setText(R.id.catalogue_tag_name, t?.itemName)
        holder?.setImageResource(R.id.catalogue_tag_iv, t?.itemIcon!!)
      }
    }
  }
}
