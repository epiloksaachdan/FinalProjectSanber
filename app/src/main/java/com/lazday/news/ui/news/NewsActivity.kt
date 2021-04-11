package com.lazday.news.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lazday.news.databinding.ActivityHomeBinding
import com.lazday.news.room.BookmarkModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val newsModule = module {
    factory { NewsActivity() }
}

class NewsActivity : AppCompatActivity() {

    private val viewModel: NewsViewModel by viewModel()
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
                BookmarkModel(count.toString(), "https://images.com", "Berita Utama")
            )
        }
    }
}