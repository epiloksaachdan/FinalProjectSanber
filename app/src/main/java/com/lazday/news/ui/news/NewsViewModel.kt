package com.lazday.news.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazday.news.retrofit.NewsModel
import com.lazday.news.retrofit.NewsRepository
import com.lazday.news.room.BookmarkModel
import kotlinx.coroutines.launch
import org.koin.dsl.module

val newsViewModel = module {
    factory { NewsViewModel(get()) }
}

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val news by lazy { MutableLiveData<NewsModel>() }
    val bookmarks = repository.bookmark.findAll()

    init {
        viewModelScope.launch {
            news.value =  repository.topHeadlines()
        }
    }

    fun add (newsSaveModel: BookmarkModel) {
        viewModelScope.launch {
            repository.bookmark.add(newsSaveModel)
        }
    }

}