package com.lazday.news.ui.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lazday.news.source.news.NewsRepository
import org.koin.dsl.module

val bookmarkViewModel = module {
    factory { BookmarkViewModel(get()) }
}

class BookmarkViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val titleBar = MutableLiveData<String>("Disimpan")
    val articles = repository.db.findAll()

}