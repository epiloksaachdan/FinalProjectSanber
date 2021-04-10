package com.lazday.news.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lazday.news.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val pageModule = module {
    factory { HomeActivity() }
}

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel.news.observe(this, {
            it.articles.forEach {  article ->
                Log.e("HomeActivity", "title: ${article.title}")
            }
        })
    }
}