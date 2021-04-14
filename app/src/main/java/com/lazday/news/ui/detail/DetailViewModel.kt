package com.lazday.news.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.source.news.NewsRepository
import kotlinx.coroutines.launch
import org.koin.dsl.module

val detailViewModel = module {
    factory { DetailViewModel(get()) }
}

class DetailViewModel(
        private val repository: NewsRepository
) : ViewModel() {

    var publishAt = ""
    val article by lazy { MutableLiveData<ArticleModel>() }

    fun find(){
        viewModelScope.launch {
            article.value = repository.db.find(publishAt)
        }
    }

    fun bookmark (articleModel: ArticleModel) {
        viewModelScope.launch {
            repository.bookmark(articleModel)
            find()
        }
    }
}