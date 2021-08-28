package com.kurantsov.data.ktor

import com.kurantsov.data.Mapper
import com.kurantsov.data.RemoteNewsDataSource
import com.kurantsov.domain.entity.NewsItem
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class RemoteNewsDataSourceImpl(
    apiKey: String,
    private val mapper: Mapper<NewsItemDTO, NewsItem> = NewsMapper()
) : RemoteNewsDataSource {

    private val client = HttpClient(provideEngine()) {
        defaultRequest {
            header(API_KEY_HEADER, apiKey)
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                prettyPrint = true

            })
        }
    }

    override suspend fun getNews(page: Int, pageSize: Int): List<NewsItem> {
        return client.get<List<NewsItemDTO>>("$BASE_URL/top-headlines") {
            parameter("country", "us")
        }.let { mapper.convertAll(it) }
    }

    private companion object {
        private const val API_KEY_HEADER = "X-Api-Key"
        private const val BASE_URL = "https://newsapi.org/v2"
    }

}