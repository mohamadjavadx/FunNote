package com.mohamadjavadx.funnote.domain.model

import kotlinx.datetime.LocalDate

data class Note(
    val id: NoteID? = null,
    val title: String,
    val isCompleted: Boolean,
    val scheduledFor: LocalDate,
)

typealias NoteID = Long