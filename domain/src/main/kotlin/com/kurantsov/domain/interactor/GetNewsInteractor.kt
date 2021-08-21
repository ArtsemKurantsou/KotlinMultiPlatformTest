package com.kurantsov.domain.interactor

import com.kurantsov.domain.NewsRepository
import com.kurantsov.domain.entity.NewsItem

class GetNewsInteractor(
    private val page: Int,
    private val repository: NewsRepository,
) : SuspendInteractor<List<NewsItem>> {
    override suspend fun execute(): List<NewsItem> {
        return repository.getNews(page)
    }
}