package com.lazday.news.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM tableBookmark")
    fun findAll(): LiveData<List<BookmarkModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(bookmarkModel: BookmarkModel)

    @Delete
    suspend fun remove(bookmarkModel: BookmarkModel)
}