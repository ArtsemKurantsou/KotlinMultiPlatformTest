package com.kurantsov.kmptest.di

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.Severity
import com.kurantsov.data.LocalNewsDataSource
import com.kurantsov.data.NewsRepositoryImpl
import com.kurantsov.data.RemoteNewsDataSource
import com.kurantsov.data.db.AppDatabase
import com.kurantsov.data.ktor.RemoteNewsDataSourceImpl
import com.kurantsov.data.sqldelight.LocalNewsDataSourceImpl
import com.kurantsov.domain.NewsRepository
import com.kurantsov.kmptest.news.NewsContract
import com.kurantsov.kmptest.news.NewsPresenter
import com.kurantsov.kmptest.newsitem.NewsItemContract
import com.kurantsov.kmptest.newsitem.NewsItemPresenter
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    return startKoin {
        properties(mapOf("API_KEY" to provideApiKey()))
        modules(
            appModule,
            databaseModule(),
            newsModule()
        )
        logger(object : Logger() {
            val logger = koin.get<Kermit> { parametersOf("Koin") }
            override fun log(level: Level, msg: MESSAGE) {
                val severity = when (level) {
                    Level.DEBUG -> Severity.Debug
                    Level.INFO -> Severity.Info
                    Level.ERROR -> Severity.Error
                    Level.NONE -> Severity.Verbose
                }
                logger.log(severity = severity, message = msg, throwable = null)
            }
        })
    }
}

private fun newsModule(): Module {
    return module {
        single { AppDatabase(get()) }
        single<LocalNewsDataSource> { LocalNewsDataSourceImpl(get()) }
        single<RemoteNewsDataSource> {
            RemoteNewsDataSourceImpl(
                getProperty("API_KEY"),
                get { parametersOf("RemoteNewsDataSourceImpl") })
        }
        single<NewsRepository> { NewsRepositoryImpl(get(), get()) }

        factory<NewsContract.Presenter> { NewsPresenter(get(), get { parametersOf("NewsPresenter") }) }
        factory<NewsItemContract.Presenter> { params ->
            NewsItemPresenter(
                params.get(),
                get(),
                get { parametersOf("NewsItemPresenter") })
        }
    }
}

expect fun provideApiKey(): String

expect fun databaseModule(): Module