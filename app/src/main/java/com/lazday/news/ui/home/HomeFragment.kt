package com.lazday.news.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.lazday.news.databinding.FragmentHomeBinding
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.ui.detail.DetailFragment
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

        binding.listNews.adapter = adapter
        viewModel.articles.observe( viewLifecycleOwner, {
            adapter.add( it )
        })

        viewModel.loading.observe(viewLifecycleOwner, binding.swipe::setRefreshing)
        viewModel.message.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.loading.postValue(false)
            }
        })

        binding.swipe.setOnRefreshListener { viewModel.fetch() }
    }

    private val adapter by lazy {
        NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener {
            override fun onBookmark(news: ArticleModel) {
                viewModel.bookmark(news)
            }
            override fun onDetail(news: ArticleModel) {
//                val detail: DetailFragment = DetailFragment()
//                val bundle = Bundle()
//                detail.show( requireActivity().supportFragmentManager, "detail" )

//                detail.arguments = bundle.putString("", "Hei")
                DetailFragment().apply {
                    arguments = bundleOf("article" to news)
                }.show(requireActivity().supportFragmentManager, "detail")

            }
        })
    }
}