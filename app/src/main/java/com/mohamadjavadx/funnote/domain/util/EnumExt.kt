package com.mohamadjavadx.funnote.domain.util

inline fun <reified T : Enum<T>> readableName(entry: T) = entry.name.replace(
    regex = "(?<=[A-Z])(?=[A-Z][a-z])|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])".toRegex(),
    replacement = " "
)