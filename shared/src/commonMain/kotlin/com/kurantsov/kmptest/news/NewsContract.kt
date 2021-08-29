package com.kurantsov.kmptest.news

import co.touchlab.kermit.Kermit
import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.kmptest.mvp.BasePresenter
import com.kurantsov.kmptest.mvp.BaseRouter
import com.kurantsov.kmptest.mvp.BaseView

interface NewsContract {
    abstract class Presenter(logger: Kermit) : BasePresenter<View>(logger) {
        abstract fun onNewsItemSelected(item: NewsItem)
    }

    interface View : BaseView {
        fun setNews(news: List<NewsItem>)
        fun createRouter(): Router
    }

    interface Router : BaseRouter {
        fun toNewsDetails(newsItem: NewsItem)
    }
}