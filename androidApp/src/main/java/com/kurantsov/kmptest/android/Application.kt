package com.kurantsov.kmptest.android

import android.app.Application
import android.content.Context
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.LogcatLogger
import com.kurantsov.kmptest.di.initKoin
import org.koin.dsl.module

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@Application }
                val baseKermit = Kermit(LogcatLogger()).withTag("BaseKermit")
                factory { (tag: String?) -> if (tag != null) baseKermit.withTag(tag) else baseKermit }
            }
        )
    }
}