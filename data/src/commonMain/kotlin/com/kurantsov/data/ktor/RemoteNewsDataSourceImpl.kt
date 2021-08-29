package com.kurantsov.data.ktor

import co.touchlab.kermit.Kermit
import com.kurantsov.data.Mapper
import com.kurantsov.data.RemoteNewsDataSource
import com.kurantsov.domain.entity.NewsItem
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

class RemoteNewsDataSourceImpl(
    apiKey: String,
    requestsLogger: Kermit,
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
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    requestsLogger.d { message }
                }
            }
        }
    }

    override suspend fun getNews(page: Int, pageSize: Int): List<NewsItem> {
        return client.get<NewsApiResponse>("$BASE_URL/top-headlines") {
            parameter("country", "us")
        }.let { response ->
            if (response.status == ResponseStatus.ok) {
                mapper.convertAll(response.articles)
            } else {
                throw Exception("Error during news fetch - ${response.code}\n${response.message}")
            }
        }
    }

    private companion object {
        private const val API_KEY_HEADER = "X-Api-Key"
        private const val BASE_URL = "https://newsapi.org/v2"
    }

}