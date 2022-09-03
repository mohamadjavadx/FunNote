package com.mohamadjavadx.funnote.domain.util

import kotlinx.datetime.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

val dateTimeFormatter: DateTimeFormatter by lazy {
    DateTimeFormatter.ofLocalizedDateTime(
        FormatStyle.MEDIUM,
        FormatStyle.SHORT,
    )
}

val dateFormatter: DateTimeFormatter by lazy {
    DateTimeFormatter.ofLocalizedDate(
        FormatStyle.FULL,
    )
}


fun Instant.toLocalDate(): LocalDate = this.toLocalDateTime(TimeZone.currentSystemDefault()).date

fun Instant.toLocalDateString():String =
    dateFormatter.format(toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()).dropLast(6)

fun Instant.toDateTimeString(): String =
    dateTimeFormatter.format(toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime())