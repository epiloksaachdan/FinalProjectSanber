package com.lazday.news.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazday.news.databinding.AdapterNewsBinding
import com.lazday.news.source.news.ArticleModel

class TestAdapter (
    var listener: OnAdapterListener,
) : ListAdapter<ArticleModel, RecyclerView.ViewHolder>(ListItemCallback()) {

    class ListItemCallback : DiffUtil.ItemCallback<ArticleModel>() {
        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.publishedAt == newItem.publishedAt
        }
        override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(val binding: AdapterNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleModel, position: Int) {
            binding.article = article
            binding.format = FormatUtil()
            itemView.setOnClickListener {
                listener.onClick( article )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterNewsBinding.inflate( LayoutInflater.from(parent.context), parent, false )
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind( getItem(position), position )
    }

    interface OnAdapterListener {
        fun onClick(article: ArticleModel)
    }
}