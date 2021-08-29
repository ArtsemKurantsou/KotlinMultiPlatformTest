package com.kurantsov.data.sqldelight

import com.kurantsov.data.LocalNewsDataSource
import com.kurantsov.data.db.AppDatabase
import com.kurantsov.data.db.News
import com.kurantsov.data.db.NewsSource
import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.domain.entity.Source
import kotlinx.datetime.Instant

class LocalNewsDataSourceImpl(
    private val database: AppDatabase
) : LocalNewsDataSource {

    override suspend fun getNews(page: Int, pageSize: Int): List<NewsItem> {
        return database
            .newsQueries
            .selectNews(page.toLong(), pageSize.toLong())
            .executeAsList()
            .map { item ->
                NewsItem(
                    id = item.url,
                    source = Source(item.sourceId, item.name),
                    author = item.author,
                    title = item.title,
                    description = item.description,
                    url = item.url,
                    imageUrl = item.imageUrl,
                    publishDate = Instant.fromEpochMilliseconds(item.publishDate),
                    content = item.content
                )
            }
    }

    override suspend fun saveNews(news: List<NewsItem>) {
        database.newsQueries.transaction {
            news.forEach {
                database.newsSourceQueries.insertSource(NewsSource(it.source.id, it.source.name))
            }
            news
                .map {
                    News(
                        sourceId = it.source.id,
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        imageUrl = it.imageUrl,
                        publishDate = it.publishDate.toEpochMilliseconds(),
                        content = it.content
                    )
                }
                .forEach {
                    database.newsQueries.insertNewsItem(it)
                }
        }
    }

    override suspend fun getNewsItemById(id: String): NewsItem? {
        return database
            .newsQueries
            .selectNewsByUrl(id)
            .executeAsOneOrNull()?.let { item ->
                NewsItem(
                    id = item.url,
                    source = Source(item.sourceId, item.name),
                    author = item.author,
                    title = item.title,
                    description = item.description,
                    url = item.url,
                    imageUrl = item.imageUrl,
                    publishDate = Instant.fromEpochMilliseconds(item.publishDate),
                    content = item.content
                )
            }
    }
}