package com.example.lvchen.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.lvchen.myapplication.R
import com.example.lvchen.myapplication.data.Word
import com.example.lvchen.myapplication.data.WordDao
import com.example.lvchen.myapplication.data.AppDatabase
import com.example.lvchen.myapplication.data.WordRepository
import kotlinx.android.synthetic.main.activity_room.insert
import kotlinx.android.synthetic.main.activity_room.textView4
import kotlinx.android.synthetic.main.activity_room.update
import java.lang.StringBuilder

class RoomActivity : AppCompatActivity() {

  private val viewModel: RoomViewModel by viewModels {
    RoomViewModelFactory(
        WordRepository(
            AppDatabase.getInstance(applicationContext)
                .getWordDao()
        )
    )

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_room)
    insert.setOnClickListener {
      viewModel.insert(Word("hello", "你好"), Word("hello2", "你好2"))
    }
    viewModel.wordLiveData.observe(this, Observer {
      var text = ""
      it.forEach {
        text += it.id.toString() + ":" + it.word + it.chineseMeaning + "\n"
      }
      textView4.text = text
    })
    update.setOnClickListener {
    }
  }

}
