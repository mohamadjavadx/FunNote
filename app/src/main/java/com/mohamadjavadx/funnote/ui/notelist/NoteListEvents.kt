package com.mohamadjavadx.funnote.ui.notelist

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.util.NoteOrder

sealed class NoteListEvents {
    object Refresh : NoteListEvents()
    data class ChangeViewOrder(val order: NoteOrder) : NoteListEvents()
    data class SelectNote(val id: Long) : NoteListEvents()
    data class DeselectNote(val id: Long) : NoteListEvents()
    object DeleteSelectedNotes : NoteListEvents()
    data class MessageShown(val message: Message) : NoteListEvents()
    object NavigateToCreateNewNote : NoteListEvents()
    data class NavigateToNoteDetail(val id: Long) : NoteListEvents()
}
