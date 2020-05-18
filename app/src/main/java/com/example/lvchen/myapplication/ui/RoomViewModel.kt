package com.example.lvchen.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvchen.myapplication.data.Word
import com.example.lvchen.myapplication.data.WordRepository
import kotlinx.coroutines.launch

/**
 * Created by journey on 2020/5/18.
 */
class RoomViewModel(private val wordRepository: WordRepository) : ViewModel() {
  val wordLiveData: LiveData<List<Word>> = wordRepository.getAll()
  fun insert(vararg word: Word) {
    viewModelScope.launch {
      wordRepository.insert(word = *word)
    }
  }
}