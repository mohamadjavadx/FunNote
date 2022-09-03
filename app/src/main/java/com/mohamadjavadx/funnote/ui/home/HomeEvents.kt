package com.mohamadjavadx.funnote.ui.home

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.model.NoteID
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

sealed interface HomeEvents {
    object Refresh : HomeEvents
    data class DraftNote(val noteTitle: String) : HomeEvents
    object AddNote : HomeEvents
    data class EditNote(val id: NoteID, val title: String) : HomeEvents
    data class ToggleNoteCompletionStatus(val id: NoteID) : HomeEvents
    data class DeleteNote(val id: NoteID) : HomeEvents
    data class SelectDate(val date: Instant) : HomeEvents
    data class MessageShown(val message: Message) : HomeEvents
}