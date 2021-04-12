package com.lazday.news.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazday.news.source.NewsRepository
import com.lazday.news.source.network.ArticleModel
import kotlinx.coroutines.launch
import org.koin.dsl.module
import java.lang.Exception

val homeViewModel = module {
    factory { HomeViewModel(get()) }
}

class HomeViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val message by lazy { MutableLiveData<String?>() }
    val articles = repository.db.newsAll()

    init {
        message.value = null
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            try {
                repository.db.saveAll(
                    repository.fetchNews().articles
                )
            } catch (e: Exception ) {
                message.value = "Terjadi kesalahan"
//                message.value = e.localizedMessage
            }
        }
    }

    fun bookmark (article: ArticleModel) {
        viewModelScope.launch {
            repository.bookmark( article )
        }
    }

}