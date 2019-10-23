package com.example.lvchen.myapplication.bean

/**
 * Created by journey on 2019-10-09.
 */
data class CataBean(
  val name: String,
  val itemIcon: Int = 0,
  var isExpend: Boolean = false,
  val cataList: ArrayList<CataItem>
)

data class CataItem(
  val itemName: String,
  val itemIcon: Int
)
