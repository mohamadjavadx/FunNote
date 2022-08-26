package com.mohamadjavadx.funnote.ui.notes

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.OrderType.Ascending
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.NoteOrder.Criteria.DateModified

data class NotesViewState(
    val noteOrder: NoteOrder = NoteOrder(DateModified, Ascending),
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
)