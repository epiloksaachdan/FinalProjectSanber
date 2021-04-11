package com.lazday.news.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lazday.news.databinding.FragmentBookmarkBinding
import com.lazday.news.retrofit.NewsModel
import com.lazday.news.room.BookmarkModel
import com.lazday.news.ui.news.BookmarkAdapter
import com.lazday.news.ui.news.NewsAdapter
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

        val adapter = BookmarkAdapter(arrayListOf(), object : BookmarkAdapter.OnAdapterListener {
            override fun onClick(bookmark: BookmarkModel) {
                viewModel.remove( bookmark )
            }
        })
        binding.listBookmark.adapter = adapter

        viewModel.bookmarks.observe(viewLifecycleOwner, {
            adapter.add( it )
        })
    }
}