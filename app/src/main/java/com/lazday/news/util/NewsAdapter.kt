package com.lazday.news.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lazday.news.databinding.AdapterNewsBinding
import com.lazday.news.source.news.ArticleModel

class NewsAdapter(
    var articles: ArrayList<ArticleModel>,
    var listener: OnAdapterListener,
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(val binding: AdapterNewsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterNewsBinding.inflate( LayoutInflater.from(parent.context), parent, false )
    )

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.article = article
        holder.binding.format = FormatUtil()
        holder.itemView.setOnClickListener {
            listener.onClick( article )
        }
    }

    fun add(data: List<ArticleModel>) {
        articles.addAll(data)
        notifyItemRangeInserted((articles.size - data.size), data.size)
    }

    fun clear() {
        articles.clear()
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(article: ArticleModel)
    }
}