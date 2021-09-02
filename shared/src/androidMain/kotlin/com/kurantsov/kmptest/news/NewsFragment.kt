package com.kurantsov.kmptest.news

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.kmptest.R
import com.kurantsov.kmptest.databinding.FragmentNewsBinding
import com.kurantsov.kmptest.mvp.BaseFragmentView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewsFragment :
    BaseFragmentView<NewsContract.View, NewsContract.Presenter>(R.layout.fragment_news),
    NewsContract.View, KoinComponent {

    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding get() = requireNotNull(_binding)

    override fun createPresenter(args: Bundle?): NewsContract.Presenter? {
        val presenter by inject<NewsContract.Presenter>()
        return presenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)
        activity?.setTitle(R.string.top_news)
        binding.rvNews.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun setNews(news: List<NewsItem>) {
        binding.rvNews.adapter = NewsAdapter(news) {
            presenter.onNewsItemSelected(it)
        }
    }

    override fun createRouter(): NewsContract.Router {
        return NewsRouter(navigator)
    }

    override fun setLoading(isLoading: Boolean) {
        with(binding) {
            rvNews.isVisible = !isLoading
            pbLoading.isVisible = isLoading
        }
    }
}