package com.jour.myapplication.ui.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jour.myapplication.data.Word
import com.jour.myapplication.databinding.ActivityRoomBinding

class RoomActivity : AppCompatActivity() {

//  private val viewModel: RoomViewModel by viewModels {
//    RoomViewModelFactory(
//        WordRepository(
//            AppDatabase.getInstance(applicationContext)
//                .getWordDao()
//        )
//    )
//
//  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.insert.setOnClickListener {
//      viewModel.insert(Word("hello1", "你好1"),Word("world2", "世界2"))
            val a = Word("insert", "你好28")
            a.id = 2
//      viewModel.update(a)
        }
        var string = "1"
        binding.update.setOnClickListener {
            string += "2"
            val a = Word(string, "你好28")
            a.id = 2
//      viewModel.update(a)
        }

//    viewModel.wordLiveData.observe(this, Observer {
//      var text = ""
//      it.forEach {
//        text += it.id.toString() + ":" + it.word + it.chineseMeaning + "\n"
//      }
//      textView4.text = text
//    })
    }

}
