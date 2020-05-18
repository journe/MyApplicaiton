package com.example.lvchen.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lvchen.myapplication.data.WordRepository

/**
 * Created by journey on 2020/5/18.
 */
class RoomViewModelFactory(private val wordRepository: WordRepository) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return RoomViewModel(wordRepository) as T
  }
}