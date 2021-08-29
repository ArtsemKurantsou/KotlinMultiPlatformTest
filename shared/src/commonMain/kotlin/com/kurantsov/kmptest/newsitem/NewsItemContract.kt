package com.kurantsov.kmptest.newsitem

import co.touchlab.kermit.Kermit
import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.kmptest.mvp.BasePresenter
import com.kurantsov.kmptest.mvp.BaseRouter
import com.kurantsov.kmptest.mvp.BaseView

interface NewsItemContract {
    abstract class Presenter(logger: Kermit) : BasePresenter<View>(logger) {
        abstract fun onOpenOriginal()
    }

    interface View : BaseView {
        fun setNewsItem(item: NewsItem)
        fun createRouter(): Router
    }

    interface Router : BaseRouter {
        fun toOriginal(url: String)
    }
}