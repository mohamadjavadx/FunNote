package com.mohamadjavadx.funnote.ui.notelist

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.Arrangement.Ascending
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.NoteOrder.Criteria.ModificationDate

data class NoteListViewState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder(ModificationDate, Ascending),
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
)