package com.lazday.news.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.lazday.news.R
import com.lazday.news.databinding.CustomToolbarBinding
import com.lazday.news.databinding.FragmentHomeBinding
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.ui.detail.DetailActivity
import com.lazday.news.util.CategoryAdapter
import com.lazday.news.util.CategoryModel
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

        binding.toolbar.view.inflateMenu(R.menu.menu_search)
        val menu = binding.toolbar.view.menu
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.fetch()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.query = it }
                return true
            }
        })

        binding.listNews.adapter = newsAdapter
        viewModel.articles.observe( viewLifecycleOwner, {
            if (it.articles.isEmpty()) {
                binding.textAlert.visibility = View.VISIBLE
                binding.listNews.visibility = View.GONE
            } else {
                binding.textAlert.visibility = View.GONE
                binding.listNews.visibility = View.VISIBLE
                newsAdapter.add( it.articles )
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.message.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.loading.postValue(false)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetch()
    }

    private val newsAdapter by lazy {
        NewsAdapter(arrayListOf(), object : NewsAdapter.OnAdapterListener {
            override fun onClick(article: ArticleModel) {
                startActivity(
                    Intent(requireActivity(), DetailActivity::class.java)
                        .putExtra("detail", article)
                )
            }
        })
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(viewModel.categories, object : CategoryAdapter.OnAdapterListener {
            override fun onClick(category: CategoryModel) {
                viewModel.category = category.id
                viewModel.fetch()
            }
        })
    }
}