package com.kurantsov.data

import com.kurantsov.domain.entity.NewsItem

interface RemoteNewsDataSource {
    suspend fun getNews(page: Int, pageSize: Int): List<NewsItem>
}