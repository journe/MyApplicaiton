package com.jour.myapplication.ui.catalogue

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jour.myapplication.R
import com.jour.myapplication.bean.CataBean
import com.jour.myapplication.bean.CataItem
import com.jour.myapplication.databinding.ActivityCatalogueBinding
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

class CatalogueActivity : AppCompatActivity() {

    val cataBeans = arrayListOf<CataBean>()

    init {

        cataBeans.add(
            CataBean(
                name = "健康养生", itemIcon = R.mipmap.ic_launcher, cataList = arrayListOf(
                    CataItem(itemName = "养生课堂", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "减肥1", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "减肥2", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "减肥3", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "减肥4", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "减肥5", itemIcon = R.mipmap.ic_launcher)
                )
            )
        )
        cataBeans.add(
            CataBean(
                name = "穿搭百科", itemIcon = R.mipmap.ic_launcher, cataList = arrayListOf(
                    CataItem(itemName = "穿搭百科1", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "穿搭百科2", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "穿搭百科3", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "穿搭百科4", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "穿搭百科5", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "穿搭百科6", itemIcon = R.mipmap.ic_launcher)
                )
            )
        )
        cataBeans.add(
            CataBean(
                name = "美食相关", itemIcon = R.mipmap.ic_launcher, cataList = arrayListOf(
                    CataItem(itemName = "美食相关1", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "美食相关2", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "美食相关3", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "美食相关4", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "美食相关5", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "美食相关6", itemIcon = R.mipmap.ic_launcher)
                )
            )
        )
        cataBeans.add(
            CataBean(
                name = "美妆个护", itemIcon = R.mipmap.ic_launcher, cataList = arrayListOf(
                    CataItem(itemName = "美妆个护1", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "美妆个护2", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "美妆个护3", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "美妆个护4", itemIcon = R.mipmap.ic_launcher),
                    CataItem(itemName = "美妆个护5", itemIcon = R.mipmap.ic_launcher)
                )
            )
        )
    }

    lateinit var binding: ActivityCatalogueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.catalogueRv.adapter = getCataAdapter(cataBeans)
        binding.catalogueItemRv.adapter = getCataItemAdapter(cataBeans[0].cataList)
    }

    private fun changeCataItem(position: Int) {
        binding.catalogueItemRv.adapter = getCataItemAdapter(cataBeans[position].cataList)
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
                holder?.getView<View>(R.id.catalogue_indicator)!!
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
