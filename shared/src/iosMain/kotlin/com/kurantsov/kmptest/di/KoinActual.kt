package com.kurantsov.kmptest.di

import co.touchlab.kermit.Kermit
import co.touchlab.kermit.NSLogLogger
import com.kurantsov.data.db.AppDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

actual fun provideApiKey(): String = "newsapi.org API KEY"

actual fun databaseModule(): Module {
    return module {
        single<SqlDriver> {
            NativeSqliteDriver(
                AppDatabase.Schema,
                "app_database"
            )
        }
    }
}

fun initKoinIos(): KoinApplication = initKoin(
    module {
        val baseKermit = Kermit(NSLogLogger()).withTag("BaseKermit")
        factory { (tag: String?) -> if (tag != null) baseKermit.withTag(tag) else baseKermit }
    }
)

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, null) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier, null)
}

fun Koin.get(objCClass: ObjCClass): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, null, null)
}