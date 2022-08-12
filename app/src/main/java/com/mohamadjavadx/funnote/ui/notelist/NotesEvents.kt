package com.mohamadjavadx.funnote.ui.notelist

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.util.NoteOrder

sealed class NotesEvents {
    object Refresh : NotesEvents()
    data class ChangeViewOrder(val order: NoteOrder) : NotesEvents()
    data class SelectNote(val id: Long) : NotesEvents()
    data class DeselectNote(val id: Long) : NotesEvents()
    object DeleteSelectedNotes : NotesEvents()
    data class MessageShown(val message: Message) : NotesEvents()
    object NavigateToCreateNewNote : NotesEvents()
    data class NavigateToNoteDetail(val id: Long) : NotesEvents()
}
