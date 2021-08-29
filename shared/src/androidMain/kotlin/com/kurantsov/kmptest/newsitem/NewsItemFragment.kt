package com.kurantsov.kmptest.newsitem

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.kmptest.R
import com.kurantsov.kmptest.databinding.FragmentNewsItemBinding
import com.kurantsov.kmptest.mvp.BaseFragmentView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class NewsItemFragment : BaseFragmentView<NewsItemContract.View, NewsItemContract.Presenter>(
    R.layout.fragment_news_item
), NewsItemContract.View, KoinComponent {

    private var _binding: FragmentNewsItemBinding? = null
    private val binding: FragmentNewsItemBinding get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsItemBinding.bind(view)
        binding.btnOpenOriginal.setOnClickListener {
            presenter.onOpenOriginal()
        }
    }

    override fun createPresenter(args: Bundle?): NewsItemContract.Presenter? {
        val newsItemId = args?.getString(ARG_NEWS_ITEM_ID) ?: return null
        val presenter: NewsItemPresenter by inject { parametersOf(newsItemId) }
        return presenter
    }

    override fun setLoading(isLoading: Boolean) {
        with(binding) {
            contentView.isVisible = !isLoading
            pbLoading.isVisible = isLoading
        }
    }

    override fun setNewsItem(item: NewsItem) {
        with(binding) {
            tvTitle.text = item.title
            tvSource.text = sequenceOf(item.source.name, item.author)
                .filterNotNull()
                .joinToString(separator = "\n")
            tvDate.text = item.publishDate.toString()
            if (item.imageUrl != null) {
                Glide.with(this@NewsItemFragment)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .dontTransform()
                    .into(ivImage)
            } else {
                ivImage.isVisible = false
            }
            tvContent.text = item.content
        }
    }

    override fun createRouter(): NewsItemContract.Router {
        return NewsItemRouter(navigator)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(newsItemId: String): NewsItemFragment {
            return NewsItemFragment().apply {
                arguments = bundleOf(ARG_NEWS_ITEM_ID to newsItemId)
            }
        }

        private const val ARG_NEWS_ITEM_ID = "ARG_NEWS_ITEM_ID"
    }
}