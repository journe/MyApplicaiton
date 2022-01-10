package com.example.lvchen.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

/**
 * Created by journey on 2020/5/18.
 */
@Database(entities = [Word::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
  abstract fun getWordDao(): WordDao

  companion object {

    const val DATABASE_NAME: String = "word_database"

    // For Singleton instantiation
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      return instance ?: synchronized(this) {
        instance ?: buildDatabase(context).also { instance = it }
      }
    }

    // Create and pre-populate the database. See this article for more details:
    // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
    private fun buildDatabase(context: Context): AppDatabase {
      return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//          .addCallback(object : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//              super.onCreate(db)
//              val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
//              WorkManager.getInstance(context)
//                  .enqueue(request)
//            }
//          })
          .allowMainThreadQueries()
          .build()
    }
  }
}