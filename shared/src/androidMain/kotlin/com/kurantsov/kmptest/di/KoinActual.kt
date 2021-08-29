package com.kurantsov.kmptest.di

import com.kurantsov.data.db.AppDatabase
import com.kurantsov.kmptest.BuildConfig
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun provideApiKey(): String {
    return BuildConfig.API_KEY
}

actual fun databaseModule(): Module {
    return module {
        single<SqlDriver> {
            AndroidSqliteDriver(
                schema = AppDatabase.Schema,
                context = get(),
                name = "app_database"
            )
        }
    }
}