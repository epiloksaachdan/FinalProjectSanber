package com.lazday.news.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lazday.news.R
import com.lazday.news.databinding.AdapterNewsBinding
import com.lazday.news.source.news.ArticleModel

class TestAdapter() : PagedListAdapter<ArticleModel, TestAdapter.ViewHolder>(diff_callback) {

    companion object {
        val diff_callback: DiffUtil.ItemCallback<ArticleModel> = object : DiffUtil.ItemCallback<ArticleModel>() {
            override fun areItemsTheSame(@NonNull oldItem: ArticleModel, @NonNull newItem: ArticleModel): Boolean {
                return oldItem.publishedAt === newItem.publishedAt
            }
            override fun areContentsTheSame(@NonNull oldItem: ArticleModel, @NonNull newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = AdapterNewsBinding.inflate( layoutInflater, parent, false )
        return ViewHolder( viewBinding )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        getItem(position)?.let {
            viewHolder.bind(it)
        }
    }

    class ViewHolder(private val binding: AdapterNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleModel) {
            binding.title.text = article.title
        }
    }
}