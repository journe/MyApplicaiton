package com.example.lvchen.myapplication.bean

/**
 * Created by journey on 2019-10-24.
 */
data class HashtagListBean(
  val `data`: Data,
  val err: Err,
  val success: Boolean
)

data class Data(
  val list: List<TagItem>
)

data class TagItem(
  val desc: String,
  val followFlag: Int,
  val id: Int,
  val name: String
)

data class Err(
  val ec: String,
  val em: String
)