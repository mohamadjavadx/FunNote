package com.mohamadjavadx.funnote.ui.home

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class HomeUiState(
    val selectedDate: Instant = Clock.System.now(),
    val visibleNotes: List<Note> = emptyList(),
    val draftNote: DraftNote = DraftNote(),
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
)

data class DraftNote(
    val title: String = "",
)

