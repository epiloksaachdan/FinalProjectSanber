package com.lazday.news.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.source.news.NewsModel
import com.lazday.news.source.news.NewsRepository
import com.lazday.news.util.CategoryModel
import kotlinx.coroutines.launch
import org.koin.dsl.module
import java.lang.Exception

val homeViewModel = module {
    factory { HomeViewModel(get()) }
}

class HomeViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val title = "Berita"
    val message by lazy { MutableLiveData<String?>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    val articles by lazy { MutableLiveData<NewsModel>() }

    init {
        message.value = null
    }

    var query = ""
    var category = ""
    fun fetch() {
        loading.value = true
        viewModelScope.launch {
            try {
                articles.value = repository.search( category, query )
                loading.value = false
            } catch (e: Exception ) {
                message.value = "Terjadi kesalahan" // e.localizedMessage
            }
        }
    }

    val categories = listOf<CategoryModel>(
            CategoryModel("", "Semua"),
            CategoryModel("business", "Bisnis"),
            CategoryModel("entertainment", "Hiburan"),
            CategoryModel("general", "Umum"),
            CategoryModel("health", "Kesehatan"),
            CategoryModel("science", "Sains"),
            CategoryModel("sports", "Olah Raga"),
            CategoryModel("technology", "Teknologi")
    )

}