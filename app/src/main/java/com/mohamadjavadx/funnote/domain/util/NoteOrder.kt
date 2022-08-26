package com.mohamadjavadx.funnote.domain.util

class NoteOrder(
    val criteria: Criteria,
    val orderType: OrderType,
) {
    enum class Criteria {
        Title,
        DateCreated,
        DateModified,
    }
}

inline fun <reified T : Enum<T>> readableName(entry: T) = entry.name.replace(
    regex = "(?<=[A-Z])(?=[A-Z][a-z])|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])".toRegex(),
    replacement = " "
)
