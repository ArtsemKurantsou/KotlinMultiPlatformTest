package com.kurantsov.kmptest.news

import co.touchlab.kermit.Kermit
import com.kurantsov.domain.NewsRepository
import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.domain.interactor.GetNewsInteractor

class NewsPresenter(
    private val repository: NewsRepository,
    logger: Kermit
) : NewsContract.Presenter(logger) {
    private var router: NewsContract.Router? = null

    override fun onBind(view: NewsContract.View) {
        super.onBind(view)
        router = view.createRouter()
    }

    override fun onViewVisible() {
        executeWithProgress {
            val items = GetNewsInteractor(0, repository).execute()
            view?.setNews(items)
        }
    }

    override fun onNewsItemSelected(item: NewsItem) {
        router?.toNewsDetails(item)
    }
}