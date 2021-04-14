package com.lazday.news.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lazday.news.R
import com.lazday.news.databinding.FragmentDetailBinding
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.util.dateFormat
import com.lazday.news.util.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val detailModule = module {
    factory { DetailFragment() }
}

class DetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = requireArguments()["article"] as ArticleModel
        Log.e("DetailFragment", article.toString())

        viewModel.publishAt = article.publishedAt
        viewModel.find()

        viewModel.article.observe(viewLifecycleOwner, {
            loadImage(binding.image, it.urlToImage)
            binding.publishedAt.text = dateFormat( it.publishedAt )
            binding.title.text = it.title
            binding.bookmark.apply {
                if (it.bookmark == 1) setImageResource(R.drawable.ic_bookmark_added)
                else setImageResource(R.drawable.ic_bookmark_add)
            }
            binding.content.text = it.content
        })

        binding.bookmark.setOnClickListener {
            viewModel.bookmark( viewModel.article.value!! )
        }
    }
}