package com.lazday.news.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.source.news.NewsRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module

val bookmarkViewModel = module {
    factory { BookmarkViewModel(get()) }
}

class BookmarkViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val articles = repository.db.newsBookmark()
    fun bookmark (articleModel: ArticleModel) {
        viewModelScope.launch {
            repository.bookmark(articleModel)
        }
    }

}