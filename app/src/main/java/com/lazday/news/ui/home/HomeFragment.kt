package com.lazday.news.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lazday.news.R
import com.lazday.news.databinding.CustomToolbarBinding
import com.lazday.news.databinding.FragmentHomeBinding
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.ui.detail.DetailFragment
import com.lazday.news.util.CategoryAdapter
import com.lazday.news.util.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { HomeFragment() }
}

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var toolbar: CustomToolbarBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        toolbar = binding.toolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title.text = viewModel.title
        binding.listCategory.adapter = categoryAdapter

        binding.toolbar.bar.inflateMenu(R.menu.menu_search)
        val menu = binding.toolbar.bar.menu
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.filter.filter(newText)
                return true
            }
        })

        binding.listNews.adapter = newsAdapter
        viewModel.articles.observe( viewLifecycleOwner, {
            newsAdapter.add( it )
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

    private val newsAdapter by lazy {
        NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener {
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

    private val categoryAdapter by lazy {
        CategoryAdapter(viewModel.categories, object : CategoryAdapter.OnAdapterListener {
            override fun onClick(category: String) {

            }
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_search, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//        val search = menu.findItem(R.id.appSearchBar)
//        val searchView = search.actionView as SearchView
//        searchView.queryHint = "Search"
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
////                adapter.filter.filter(newText)
//                return true
//            }
//        })
//    }
}