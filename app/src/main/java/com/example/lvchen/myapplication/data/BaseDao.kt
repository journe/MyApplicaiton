package com.example.lvchen.myapplication.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Created by journey on 2020/5/18.
 */
interface BaseDao<T> {
  @Insert
  suspend fun insert(vararg obj: T)

  @Update
  suspend fun update(vararg obj: T)

  @Delete
  fun delete(vararg obj: T)

}