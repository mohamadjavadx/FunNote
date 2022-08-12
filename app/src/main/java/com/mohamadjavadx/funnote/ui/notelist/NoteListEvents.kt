package com.mohamadjavadx.funnote.ui.notelist

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.util.NoteOrder

sealed class NoteListEvents {
    object Refresh : NoteListEvents()
    data class ChangeViewOrder(val noteOrder: NoteOrder) : NoteListEvents()
    data class DeleteNotes(val noteIds: List<Long>) : NoteListEvents()
    data class MessageShown(val message: Message): NoteListEvents()
    object NavigateToCreateNewNote : NoteListEvents()
    data class NavigateToNoteDetail(val noteId: Long) : NoteListEvents()
}
