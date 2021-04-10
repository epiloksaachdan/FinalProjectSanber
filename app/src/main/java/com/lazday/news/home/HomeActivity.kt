package com.lazday.news.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lazday.news.R
import com.lazday.news.databinding.ActivityHomeBinding
import com.lazday.news.room.NewsSaveModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { HomeActivity() }
}

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = NewsAdapter(arrayListOf())
        binding.listNews.adapter = adapter

        viewModel.news.observe(this, {
//            it.articles.forEach {  article ->
//                Log.e("HomeActivity", "fetch: ${article.title}")
//            }
            adapter.add( it.articles )
        })

        viewModel.bookmarks.observe(this, {
            it.forEach {  article ->
                Log.e("HomeActivity", "bookmark: ${article.publishedAt} ${article.title}")
            }
        })

        var count = 0;
        binding.addTest.setOnClickListener {

            count ++
            viewModel.add(
                NewsSaveModel(count.toString(), "https://images.com", "Berita Utama")
            )
        }
    }
}