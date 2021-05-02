package com.lazday.news.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lazday.news.R
import com.lazday.news.databinding.AdapterNewsBinding
import com.lazday.news.source.news.ArticleModel

class NewsAdapter(
    var articles: ArrayList<ArticleModel>,
    var listener: OnAdapterListener,
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]

        holder.binding.title.text = article.title
        holder.binding.publishedAt.text = dateFormat( article.publishedAt )
        loadImage(holder.binding.image, article.urlToImage)

        holder.itemView.setOnClickListener {
            listener.onClick( article )
        }
    }

    class ViewHolder(val binding: AdapterNewsBinding): RecyclerView.ViewHolder(binding.root)

    fun add(data: List<ArticleModel>) {
        articles.clear()
        articles.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(article: ArticleModel)
    }
}