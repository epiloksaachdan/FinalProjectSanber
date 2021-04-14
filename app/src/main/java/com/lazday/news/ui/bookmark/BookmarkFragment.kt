package com.lazday.news.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lazday.news.databinding.FragmentBookmarkBinding
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.ui.detail.DetailFragment
import com.lazday.news.util.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    factory { BookmarkFragment() }
}

class BookmarkFragment : Fragment() {

    private val viewModel: BookmarkViewModel by viewModel()
    private lateinit var binding: FragmentBookmarkBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listBookmark.adapter = adapter
        viewModel.articles.observe(viewLifecycleOwner, {
            adapter.add( it )
        })
    }

    private val adapter by lazy {
        NewsAdapter(arrayListOf(), object: NewsAdapter.OnAdapterListener {
            override fun onBookmark(article: ArticleModel) {
                viewModel.bookmark(article)
            }
            override fun onDetail(article: ArticleModel) {
                DetailFragment().apply {
                    arguments = bundleOf("published_at" to article.publishedAt)
                }.show(requireActivity().supportFragmentManager, "detail")
            }
        })
    }
}