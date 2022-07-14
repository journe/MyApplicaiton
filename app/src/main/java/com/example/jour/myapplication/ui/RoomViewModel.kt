package com.example.jour.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jour.myapplication.data.Word
import com.example.jour.myapplication.data.WordRepository
import kotlinx.coroutines.launch

/**
 * Created by journey on 2020/5/18.
 */
class RoomViewModel() : ViewModel() {
//  private val wordRepository: WordRepository
//  val wordLiveData: LiveData<List<Word>> = wordRepository.getAll()
  fun insert(vararg word: Word) {
    viewModelScope.launch {
//      wordRepository.insert(word = *word)
    }
  }

  fun update(vararg word: Word) {
    viewModelScope.launch {
//      wordRepository.update(word = *word)
    }
  }
}