package com.kurantsov.data.ktor

import io.ktor.client.engine.*
import io.ktor.client.engine.android.*

actual fun provideEngine(): HttpClientEngine = Android.create()