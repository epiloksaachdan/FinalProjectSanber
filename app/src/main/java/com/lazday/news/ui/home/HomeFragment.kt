package com.lazday.news.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lazday.news.databinding.FragmentHomeBinding
import com.lazday.news.source.network.ArticleModel
import com.lazday.news.util.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { HomeFragment() }
}

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener {
            override fun onClick(news: ArticleModel) {
                viewModel.bookmark(news)
            }
        })
        binding.listNews.adapter = adapter

        viewModel.message.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.articles.observe( viewLifecycleOwner, {
            adapter.add( it )
            binding.swipe.isRefreshing = false
        })

        binding.swipe.setOnRefreshListener {
            viewModel.fetch()
        }
    }
}