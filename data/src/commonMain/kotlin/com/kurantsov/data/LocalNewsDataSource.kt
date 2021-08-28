package com.kurantsov.data

import com.kurantsov.domain.entity.NewsItem

interface LocalNewsDataSource {
    suspend fun getNews(page: Int, pageSize: Int): List<NewsItem>
    suspend fun saveNews(news: List<NewsItem>)
    suspend fun getNewsItemById(id: String): NewsItem?
}