package com.lazday.news.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsSaveDao {

    @Query("SELECT * FROM tableSave")
    fun findAll(): LiveData<List<NewsSaveModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(newsSave: NewsSaveModel)
}