package com.kurantsov.data

import com.kurantsov.domain.NewsRepository
import com.kurantsov.domain.entity.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class NewsRepositoryImpl(
    private val remoteStore: RemoteNewsDataSource,
    private val localStore: LocalNewsDataSource,
    private val updatePeriodMilliseconds: Long = DEFAULT_UPDATE_PERIOD,
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
    private val context: CoroutineContext = Dispatchers.Default
) : NewsRepository {

    private var pagesUpdatesTime: MutableList<Long> = mutableListOf()

    override suspend fun getNews(page: Int): List<NewsItem> = withContext(context) {
        return@withContext if (shouldUpdatePage(page)) {
            val news = remoteStore.getNews(page, pageSize)
            localStore.saveNews(news)
            setPageUpdated(page)
            news
        } else {
            localStore.getNews(page, pageSize)
        }
    }

    override suspend fun getNewsItem(id: String): NewsItem? = withContext(context) {
        return@withContext localStore.getNewsItemById(id)
    }

    private fun shouldUpdatePage(page: Int): Boolean {
        return page >= pagesUpdatesTime.size
                || currentTimeMillis() - pagesUpdatesTime[page] >= updatePeriodMilliseconds
    }

    private fun setPageUpdated(page: Int) {
        val timeUpdated = currentTimeMillis()
        if (page < pagesUpdatesTime.size) {
            pagesUpdatesTime[page] = timeUpdated
        } else {
            pagesUpdatesTime.add(page, timeUpdated)
        }
    }

    private fun currentTimeMillis(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }

    private companion object {
        @OptIn(ExperimentalTime::class)
        private val DEFAULT_UPDATE_PERIOD = Duration.hours(1).inWholeMilliseconds
        private const val DEFAULT_PAGE_SIZE = 50
    }
}