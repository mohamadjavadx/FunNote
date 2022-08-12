package com.mohamadjavadx.funnote.ui.notelist

import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.Arrangement.Ascending
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.NoteOrder.Criteria.ModificationDate

data class NoteListViewState(
    val noteOrder: NoteOrder = NoteOrder(ModificationDate, Ascending),
    val notes: List<Note> = emptyList(),
    val selectedNotes: Set<Long> = emptySet(),
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
)