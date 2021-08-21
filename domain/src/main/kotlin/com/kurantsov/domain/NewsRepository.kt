package com.kurantsov.domain

import com.kurantsov.domain.entity.NewsItem

interface NewsRepository {
    suspend fun getNews(page: Int): List<NewsItem>
    suspend fun getNewsItem(id: String): NewsItem?
}