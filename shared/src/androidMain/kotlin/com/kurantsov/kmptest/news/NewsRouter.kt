package com.kurantsov.kmptest.news

import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.kmptest.mvp.Navigator
import com.kurantsov.kmptest.newsitem.NewsItemFragment

class NewsRouter(private val navigator: Navigator) : NewsContract.Router {
    override fun toNewsDetails(newsItem: NewsItem) {
        navigator.openFragment(NewsItemFragment.newInstance(newsItem.id))
    }
}