package com.mohamadjavadx.funnote.ui.allnotes

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.NoteOrder

sealed interface AllNotesEvents {
    object Refresh : AllNotesEvents
    data class UpdateOrder(val order: NoteOrder) : AllNotesEvents
    data class DeleteNote(val id: Long) : AllNotesEvents
    data class MessageShown(val message: Message) : AllNotesEvents
}

sealed interface AllNotesNavigationEvent{
    data class NavigateToNote(val note:Note) : AllNotesNavigationEvent
}
