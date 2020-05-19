package com.example.lvchen.myapplication.data

import androidx.lifecycle.LiveData

/**
 * Created by journey on 2020/5/18.
 */
class WordRepository(private val wordDao: WordDao) {

  fun getAll(): LiveData<List<Word>> {
    return wordDao.getAllLive()
  }

  suspend fun insert(vararg word: Word) {
    wordDao.insert(*word)
  }
  suspend fun update(vararg word: Word) {
    wordDao.update(*word)
  }

}