package com.lazday.news.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lazday.news.R
import com.lazday.news.databinding.AdapterNewsBinding
import com.lazday.news.retrofit.NewsModel
import com.lazday.news.room.BookmarkModel
import com.lazday.news.util.dateFormat

class BookmarkAdapter(
    var articles: ArrayList<BookmarkModel>,
    var listener: OnAdapterListener?,
) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

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
        Glide.with(holder.binding.image)
            .load( article.urlImage )
            .into(holder.binding.image)
        holder.binding.bookmark.setImageResource(R.drawable.ic_bookmark_remove)
        holder.binding.bookmark.setOnClickListener {
            listener?.onClick( article )
        }
    }

    class ViewHolder(val binding: AdapterNewsBinding): RecyclerView.ViewHolder(binding.root)

    fun add(data: List<BookmarkModel>) {
        articles.clear()
        articles.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(news: BookmarkModel)
    }
}