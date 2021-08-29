package com.kurantsov.data.ktor

import kotlinx.serialization.Serializable

@Serializable
data class NewsApiResponse(
    val status: ResponseStatus,
    val totalResults: Int,
    val articles: List<NewsItemDTO>,
    val code: String? = null,
    val message: String? = null
)

@Serializable
enum class ResponseStatus {
    ok,
    error
}