package com.mohamadjavadx.funnote.data.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}

class LocalDateConverter {
    @TypeConverter
    fun longToInstant(value: String?): LocalDate? =
        value?.let(LocalDate.Companion::parse)

    @TypeConverter
    fun instantToLong(localDate: LocalDate?): String? =
        localDate?.toString()
}