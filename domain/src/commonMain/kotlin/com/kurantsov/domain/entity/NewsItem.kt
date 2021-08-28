package com.kurantsov.domain.entity

import kotlinx.datetime.Instant

data class NewsItem(
    val id: String,
    val source: Source,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val publishDate: Instant,
    val content: String?,
)
