package com.mohamadjavadx.funnote.domain.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

val formatter: DateTimeFormatter by lazy {
    DateTimeFormatter.ofLocalizedDateTime(
        FormatStyle.MEDIUM,
        FormatStyle.SHORT,
    )
}

fun Instant.toDateTimeString(): String =
    formatter.format(toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime())