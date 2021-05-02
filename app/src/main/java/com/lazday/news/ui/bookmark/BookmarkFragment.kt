package com.lazday.news.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lazday.news.databinding.CustomToolbarBinding
import com.lazday.news.databinding.FragmentBookmarkBinding
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.ui.detail.DetailActivity
import com.lazday.news.util.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    factory { BookmarkFragment() }
}

class BookmarkFragment : Fragment() {

    private val viewModel: BookmarkViewModel by viewModel()
    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var toolbar: CustomToolbarBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        toolbar = binding.toolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.titleBar.observe( viewLifecycleOwner, {
            toolbar.title.text = it
        })

        binding.listBookmark.adapter = adapter
        viewModel.articles.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                binding.textAlert.visibility = View.VISIBLE
                binding.listBookmark.visibility = View.GONE
            } else {
                binding.textAlert.visibility = View.GONE
                binding.listBookmark.visibility = View.VISIBLE
                adapter.add( it )
            }
        })
    }

    private val adapter by lazy {
        NewsAdapter(arrayListOf(), object: NewsAdapter.OnAdapterListener {
            override fun onClick(article: ArticleModel) {
                startActivity(
                        Intent(requireActivity(), DetailActivity::class.java)
                                .putExtra("detail", article)
                )
            }
        })
    }
}