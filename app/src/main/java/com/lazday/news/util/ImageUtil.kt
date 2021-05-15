package com.lazday.news.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, urlString: String?){
    urlString?.let {
        Glide.with(imageView)
                .load( urlString )
                .into( imageView )
    }
}