package com.example.lvchen.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * Created by journey on 2020/5/18.
 */
@Dao
interface WordDao : BaseDao<Word> {

  @Query("DELETE FROM WORD")
  fun deleteAll()

  @Query("SELECT * FROM WORD ORDER BY ID DESC")
  fun getAll(): List<Word>

  @Query("SELECT * FROM WORD ORDER BY ID DESC")
  fun getAllLive(): LiveData<List<Word>>

}