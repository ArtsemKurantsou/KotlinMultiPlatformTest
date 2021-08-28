package com.kurantsov.data.ktor

import io.ktor.client.engine.*
import io.ktor.client.engine.ios.*

actual fun provideEngine(): HttpClientEngine = Ios.create()