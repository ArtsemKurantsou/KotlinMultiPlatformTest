package com.kurantsov.kmptest.newsitem

import co.touchlab.kermit.Kermit
import com.kurantsov.domain.NewsRepository
import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.domain.interactor.GetNewsItemInteractor

class NewsItemPresenter(
    private val newsItemId: String,
    private val repository: NewsRepository,
    logger: Kermit
) : NewsItemContract.Presenter(logger) {
    private lateinit var newsItem: NewsItem

    private var router: NewsItemContract.Router? = null

    override fun onBind(view: NewsItemContract.View) {
        super.onBind(view)
        router = view.createRouter()
        executeWithProgress {
            val item = GetNewsItemInteractor(newsItemId, repository).execute()
            if (item == null) {
                this.view?.showError("No news item with id = $newsItem")
            } else {
                newsItem = item
                this.view?.setNewsItem(item)
            }
        }
    }

    override fun onViewVisible() {
        super.onViewVisible()
        if (::newsItem.isInitialized) {
            view?.setNewsItem(newsItem)
        }
    }

    override fun onOpenOriginal() {
        router?.toOriginal(newsItem.url)
    }

    override fun onUnbind() {
        super.onUnbind()
        router = null
    }

}