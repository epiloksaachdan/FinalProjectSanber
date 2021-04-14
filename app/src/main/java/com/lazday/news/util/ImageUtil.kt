package com.lazday.news.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun loadImage(imageView: ImageView, urlString: String?){
    urlString?.let {
        Glide.with(imageView)
                .load( urlString )
                .into( imageView )
    }
}