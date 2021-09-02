package com.kurantsov.kmptest.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.toDisplayFormat(): String {
    val localDate = toLocalDateTime(TimeZone.currentSystemDefault())
    return with(localDate) {
        "$monthNumber/$dayOfMonth/$year $hour:$minute"
    }
}