package com.lazday.news.ui.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazday.news.retrofit.NewsModel
import com.lazday.news.retrofit.NewsRepository
import com.lazday.news.room.BookmarkModel
import com.lazday.news.ui.news.NewsViewModel
import kotlinx.coroutines.launch
import org.koin.dsl.module

val bookmarkViewModel = module {
    factory { BookmarkViewModel(get()) }
}

class BookmarkViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val news by lazy { MutableLiveData<NewsModel>() }
    val bookmarks = repository.bookmark.findAll()

    init {
        viewModelScope.launch {
            news.value =  repository.topHeadlines()
        }
    }

    fun remove (bookmark: BookmarkModel) {
        viewModelScope.launch {
            repository.bookmark.remove(bookmark)
        }
    }

}