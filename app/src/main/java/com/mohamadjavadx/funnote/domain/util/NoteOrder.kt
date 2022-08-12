package com.mohamadjavadx.funnote.domain.util

class NoteOrder(
    val criteria: Criteria,
    val arrangement: Arrangement,
) {
    enum class Criteria {
        Title,
        CreationDate,
        ModificationDate,
    }
}