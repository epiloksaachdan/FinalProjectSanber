package com.lazday.news.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import com.lazday.news.R
import com.lazday.news.databinding.CustomToolbarBinding
import com.lazday.news.databinding.FragmentHomeBinding
import com.lazday.news.source.news.ArticleModel
import com.lazday.news.ui.detail.DetailActivity
import com.lazday.news.util.CategoryAdapter
import com.lazday.news.util.CategoryModel
import com.lazday.news.util.NewsAdapter
import com.lazday.news.util.TestAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.module
import timber.log.Timber
import kotlin.math.ceil

val homeModule = module {
    factory { HomeFragment() }
}

class HomeFragment : Fragment(), TestAdapter.OnAdapterListener {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var toolbar: CustomToolbarBinding

//    private lateinit var testAdapter: TestAdapter
    override fun onClick(article: ArticleModel) {

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        toolbar = binding.toolbar
//        testAdapter = TestAdapter(this)
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
//        binding.listNews.adapter = testAdapter
        viewModel.articles.observe( viewLifecycleOwner, Observer {
            if (it.articles.isEmpty()) {
                binding.imageAlert.visibility = View.VISIBLE
                binding.textAlert.visibility = View.VISIBLE
                binding.listNews.visibility = View.GONE
            } else {
                binding.imageAlert.visibility = View.GONE
                binding.textAlert.visibility = View.GONE
                binding.listNews.visibility = View.VISIBLE

                newsAdapter.add( it.articles )
//                testAdapter.submitList( it.articles )
                Timber.e("articleSize: ${it.articles.size}")
            }
        } )


        viewModel.category.observe(viewLifecycleOwner, Observer {
            Timber.e("category ${viewModel.category.value}")
            newsAdapter.clear()
            binding.scroll.scrollTo(0, 0)
            viewModel.page = 1
            viewModel.total = 1
            viewModel.fetch()
        })

        binding.scroll.setOnScrollChangeListener {
                v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY == v?.getChildAt(0)!!.measuredHeight - v.measuredHeight) {
                if (viewModel.page <= viewModel.total) viewModel.fetch()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (viewModel.page == 1)
                binding.progressTop.visibility = if (it) View.VISIBLE else View.GONE
            else {
                binding.progressBottom.visibility = if (it) View.VISIBLE else View.GONE
                binding.progressTop.visibility = View.GONE
            }
        })
        viewModel.message.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.loading.postValue(false)
            }
        })
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
                viewModel.category.postValue(category.id)
            }
        })
    }
}