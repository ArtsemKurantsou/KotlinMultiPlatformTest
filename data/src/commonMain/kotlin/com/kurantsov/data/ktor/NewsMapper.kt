package com.kurantsov.data.ktor

import com.kurantsov.data.Mapper
import com.kurantsov.domain.entity.NewsItem
import com.kurantsov.domain.entity.Source
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class NewsMapper : Mapper<NewsItemDTO, NewsItem> {
    override fun convert(input: NewsItemDTO): NewsItem {
        val source = Source(
            id = input.source.id ?: input.source.name,
            name = input.source.name
        )
        return NewsItem(
            id = input.url,
            source = source,
            author = input.author,
            title = input.title,
            description = input.description,
            url = input.url,
            imageUrl = input.urlToImage,
            publishDate = parseDate(input.publishedAt),
            content = input.content
        )
    }

    override fun convertBack(input: NewsItem): NewsItemDTO {
        return NewsItemDTO(
            source = SourceDTO(id = input.source.id, name = input.source.name),
            author = input.author,
            title = input.title,
            description = input.description,
            url = input.url,
            urlToImage = input.imageUrl,
            publishedAt = input.publishDate.toLocalDateTime(TimeZone.UTC).toString(),
            content = input.content
        )
    }

    private fun parseDate(date: String): Instant {
        return Instant.parse(date)
    }
}