package com.lazday.news.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazday.news.source.news.NewsModel
import com.lazday.news.source.news.NewsRepository
import com.lazday.news.ui.news.CategoryModel
import kotlinx.coroutines.launch
import org.koin.dsl.module
import timber.log.Timber
import java.lang.Exception
import kotlin.math.ceil

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
    val category by lazy { MutableLiveData<String>("") }

    init {
        message.value = null
        fetch()
    }

    var query = ""
    var page = 1
    var total = 1

    fun fetch() {
        Timber.e("fetchPage: $page")
        loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.page( category.value, query, page )
                articles.value = response
                val totalResults: Double = response.totalResults / 20.0
                total = ceil(totalResults).toInt()
                page ++
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