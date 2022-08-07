package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.Arrangement.Ascending
import com.mohamadjavadx.funnote.domain.util.Arrangement.Descending
import com.mohamadjavadx.funnote.domain.util.DataState
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.NoteOrder.Criteria.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotes(
    private val repository: NoteRepository,
) {
    operator fun invoke(noteOrder: NoteOrder): Flow<DataState<List<Note>>> =
        repository.getAllNotes().map { dataState ->
            val notes: List<Note> = dataState.data ?: return@map dataState
            when (noteOrder.arrangement) {
                Ascending -> {
                    when (noteOrder.criteria) {
                        Title -> dataState.copy(data = notes.sortedBy { it.title.lowercase() })
                        CreatedDate -> dataState.copy(data = notes.sortedBy { it.createdAt })
                        ModificationDate -> dataState.copy(data = notes.sortedBy { it.updatedAt })
                    }
                }
                Descending -> {
                    when (noteOrder.criteria) {
                        Title -> dataState.copy(data = notes.sortedByDescending { it.title.lowercase() })
                        CreatedDate -> dataState.copy(data = notes.sortedByDescending { it.createdAt })
                        ModificationDate -> dataState.copy(data = notes.sortedByDescending { it.updatedAt })
                    }
                }
            }
        }

}