package com.example.lvchen.myapplication.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lvchen.myapplication.R
import com.example.lvchen.myapplication.R.mipmap
import com.example.lvchen.myapplication.bean.CataBean
import com.example.lvchen.myapplication.bean.CataItem
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.activity_catalogue2.*

class Catalogue2Activity : AppCompatActivity() {

  val cataBeans = arrayListOf<CataBean>()

  init {
    cataBeans.add(
        CataBean(
            name = "健康养生", itemIcon = mipmap.ic_launcher_round, cataList = arrayListOf(
            CataItem(itemName = "养生课堂", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "减肥1", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "减肥2", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "减肥3", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "减肥4", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "减肥5", itemIcon = mipmap.ic_launcher_round)
        )
        )
    )
    cataBeans.add(
        CataBean(
            name = "穿搭百科", itemIcon = mipmap.ic_launcher_round, cataList = arrayListOf(
            CataItem(itemName = "穿搭百科1", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科2", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科3", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科4", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科5", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "穿搭百科6", itemIcon = mipmap.ic_launcher_round)
        )
        )
    )
    cataBeans.add(
        CataBean(
            name = "美食相关", itemIcon = mipmap.ic_launcher_round, cataList = arrayListOf(
            CataItem(itemName = "美食相关1", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关2", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关3", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关4", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关5", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "美食相关6", itemIcon = mipmap.ic_launcher_round)
        )
        )
    )
    cataBeans.add(
        CataBean(
            name = "美妆个护", itemIcon = mipmap.ic_launcher_round, cataList = arrayListOf(
            CataItem(itemName = "美妆个护1", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "美妆个护2", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "美妆个护3", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "美妆个护4", itemIcon = mipmap.ic_launcher_round),
            CataItem(itemName = "美妆个护5", itemIcon = mipmap.ic_launcher_round)
        )
        )
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_catalogue2)
    cata2_rv.adapter = getCataAdapter(cataBeans)
  }

  private fun getCataAdapter(cataBeans: ArrayList<CataBean>): CommonAdapter<CataBean> {
    return object : CommonAdapter<CataBean>(this, R.layout.item_catalogue_grid, cataBeans) {
      var lastPosition = 0
      override fun convert(
        holder: ViewHolder?,
        t: CataBean?,
        position: Int
      ) {
        holder?.setText(R.id.catalogue_tag_name, t?.name)
        holder?.setImageResource(R.id.catalogue_tag_iv, t?.itemIcon!!)
//        holder?.setOnClickListener(
//            R.id.linearLayout
//        ) { v ->
//          if (position != lastPosition) {
//            v.isSelected = true
//            notifyDataSetChanged()
//          }
//          lastPosition = position
//        }
//        holder?.getView<View>(R.id.linearLayout)!!.isSelected = lastPosition == position
      }
    }
  }
}
