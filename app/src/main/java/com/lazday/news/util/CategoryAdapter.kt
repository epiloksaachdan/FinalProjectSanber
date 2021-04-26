package com.lazday.news.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lazday.news.R
import com.lazday.news.databinding.AdapterCategoryBinding

class CategoryAdapter(
        var categories: List<String>,
        var listener: OnAdapterListener,
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val items = arrayListOf<TextView>()

    class ViewHolder(val binding: AdapterCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.category.text = category
        items.add( holder.binding.category )
        holder.itemView.setOnClickListener {
            listener.onClick( category )
            setColor( holder.binding.category )
        }
    }

    interface OnAdapterListener {
        fun onClick(category: String)
    }

    private fun setColor(textView: TextView){
        items.forEach {
            it.setBackgroundResource(R.color.white)
        }
        textView.setBackgroundResource(android.R.color.darker_gray)
    }
}