package com.jour.myapplication.ui.catalogue

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jour.myapplication.R
import com.jour.myapplication.R.mipmap
import com.jour.myapplication.bean.CataBean
import com.jour.myapplication.bean.CataItem
import com.jour.myapplication.ui.TopicListActivity
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.activity_catalogue2.cata2_rv

class Catalogue2Activity : AppCompatActivity() {

  val cataBeans = arrayListOf<CataBean>()

  init {
    cataBeans.add(
        CataBean(
            name = "健康养生", itemIcon = mipmap.ic_launcher, cataList = arrayListOf(
            CataItem(itemName = "养生课堂", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "减肥1", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "减肥2", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "减肥3", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "减肥4", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "减肥5", itemIcon = mipmap.ic_launcher)
        )
        )
    )
    cataBeans.add(
        CataBean(
            name = "穿搭百科", itemIcon = mipmap.ic_launcher, cataList = arrayListOf(
            CataItem(itemName = "穿搭百科1", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "穿搭百科2", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "穿搭百科3", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "穿搭百科4", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "穿搭百科5", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "穿搭百科6", itemIcon = mipmap.ic_launcher)
        )
        )
    )
    cataBeans.add(
        CataBean(
            name = "美食相关", itemIcon = mipmap.ic_launcher, cataList = arrayListOf(
            CataItem(itemName = "美食相关1", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "美食相关2", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "美食相关3", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "美食相关4", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "美食相关5", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "美食相关6", itemIcon = mipmap.ic_launcher)
        )
        )
    )
    cataBeans.add(
        CataBean(
            name = "美妆个护", itemIcon = mipmap.ic_launcher, cataList = arrayListOf(
            CataItem(itemName = "美妆个护1", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "美妆个护2", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "美妆个护3", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "美妆个护4", itemIcon = mipmap.ic_launcher),
            CataItem(itemName = "美妆个护5", itemIcon = mipmap.ic_launcher)
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
      var count = cataBeans.size
      override fun convert(
        holder: ViewHolder?,
        bean: CataBean?,
        position: Int
      ) {
        //二级菜单
        if (bean!!.cataList.isEmpty()) {
          holder?.setBackgroundColor(R.id.catalogue_tag_iv, Color.WHITE)
        } else {
          holder?.setBackgroundColor(R.id.catalogue_tag_iv, Color.GRAY)
        }
        holder?.setText(R.id.catalogue_tag_name, bean?.name)
        holder?.setImageResource(R.id.catalogue_tag_iv, bean?.itemIcon!!)

        holder?.setOnClickListener(
            R.id.catalogue_tag_cl
        ) { v ->
          if (bean.cataList.isEmpty()) {
            startActivity(Intent(this@Catalogue2Activity, TopicListActivity::class.java))
            return@setOnClickListener
          }
          if (!bean.isExpend) {
            val cata2 = bean.cataList.map {
              CataBean(it.itemName, it.itemIcon, cataList = arrayListOf())
            }
            count += cata2.size
            cataBeans.addAll(position + 1, cata2)

            //插入或者移除后其他的item中的position不会变
            notifyItemRangeInserted(position + 1, cata2.size)
            //刷新后面的item更新position
            notifyItemRangeChanged(position + cata2.size, count)
//            notifyItemRangeChanged(0, count, "change_position")
//            notifyDataSetChanged()
          } else {
            count -= bean.cataList.size
            bean.cataList.forEach { item ->
              cataBeans.removeAll { it.name == item.itemName }
            }
//            notifyItemMoved(position + 1, position + 1 + bean.cataList.size)
            notifyDataSetChanged()
          }
          bean.isExpend = !bean.isExpend
        }

      }

//      override fun onBindViewHolder(
//        holder: ViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//      ) {
//        if (payloads.isEmpty()) {
//          super.onBindViewHolder(holder, position, payloads)
//        } else {
//          val str = payloads[0] as String
//          Logger.d(position)
//          holder.setTag(R.id.catalogue_tag_name, position)
//          holder.position
//        }
//      }

      override fun getItemCount(): Int {
        return count
      }
    }
  }
}
