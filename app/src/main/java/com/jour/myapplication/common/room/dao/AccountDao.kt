package com.jour.myapplication.common.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jour.myapplication.common.model.db.AccountBean

@Dao
interface AccountDao {
    @Query("SELECT * FROM AccountBean WHERE id = :id LIMIT 1")
    fun getUserById(id: Int): AccountBean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: AccountBean)
}