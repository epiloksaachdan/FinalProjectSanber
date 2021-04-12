package com.lazday.news.source.news

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {

    @Query("SELECT * FROM tableArticle")
    fun newsAll(): LiveData<List<ArticleModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(listArticle: List<ArticleModel>)

    @Update
    suspend fun bookmark(article: ArticleModel)

    @Query("SELECT * FROM tableArticle WHERE bookmark=1 ")
    fun newsBookmark(): LiveData<List<ArticleModel>>
}