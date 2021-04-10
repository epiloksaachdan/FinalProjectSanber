package com.lazday.news.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazday.news.retrofit.NewsModel
import com.lazday.news.retrofit.NewsRepository
import com.lazday.news.room.NewsSaveModel
import kotlinx.coroutines.launch
import org.koin.dsl.module

val homeViewModel = module {
    factory { HomeViewModel(get()) }
}

class HomeViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val news by lazy { MutableLiveData<NewsModel>() }
    val bookmarks = repository.bookmarks()

    init {
        viewModelScope.launch {
            news.value =  repository.topHeadlines()
        }
    }

    fun add (newsSaveModel: NewsSaveModel) {
        viewModelScope.launch {
            repository.add(newsSaveModel)
        }
    }

}