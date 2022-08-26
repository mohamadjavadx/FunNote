package com.mohamadjavadx.funnote.ui.notes

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.util.NoteOrder

sealed class NotesEvents {
    object Refresh : NotesEvents()
    data class UpdateOrder(val order: NoteOrder) : NotesEvents()
    data class DeleteNote(val id: Long) : NotesEvents()
    data class MessageShown(val message: Message) : NotesEvents()
}

sealed class NoteNavigationEvent{
    object NavigateToCreateNewNote : NoteNavigationEvent()
    data class NavigateToNoteDetail(val id: Long) : NoteNavigationEvent()
}
