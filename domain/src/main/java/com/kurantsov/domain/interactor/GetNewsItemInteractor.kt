package com.kurantsov.domain.interactor

import com.kurantsov.domain.NewsRepository
import com.kurantsov.domain.entity.NewsItem

class GetNewsItemInteractor(
    private val newsItemId: String,
    private val repository: NewsRepository,
) : SuspendInteractor<NewsItem?> {
    override suspend fun execute(): NewsItem? {
        return repository.getNewsItem(newsItemId)
    }
}