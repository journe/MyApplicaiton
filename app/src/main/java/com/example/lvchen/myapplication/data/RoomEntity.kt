package com.example.lvchen.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by journey on 2020/5/18.
 */
@Entity
data class Word(
//  @ColumnInfo(name = "word")
  val word: String,
//  @ColumnInfo(name = "chinese")
  val chineseMeaning: String
) {
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0
}