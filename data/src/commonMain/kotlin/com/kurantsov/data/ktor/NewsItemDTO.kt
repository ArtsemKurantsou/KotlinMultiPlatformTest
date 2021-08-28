package com.kurantsov.data.ktor

import kotlinx.serialization.Serializable

@Serializable
data class NewsItemDTO(
    val source: SourceDTO,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
)
