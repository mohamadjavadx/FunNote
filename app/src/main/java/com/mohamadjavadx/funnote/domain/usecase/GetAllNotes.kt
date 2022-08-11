package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.Arrangement.Ascending
import com.mohamadjavadx.funnote.domain.util.Arrangement.Descending
import com.mohamadjavadx.funnote.domain.util.Result
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.NoteOrder.Criteria.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotes(
    private val repository: NoteRepository,
) {
    operator fun invoke(noteOrder: NoteOrder): Flow<Result<List<Note>>> =
        repository.getAllNotes().map { result ->
            if (result is Result.Success) {
                val notes = result.data
                when (noteOrder.arrangement) {
                    Ascending -> {
                        when (noteOrder.criteria) {
                            Title -> result.copy(data = notes.sortedBy { it.title.lowercase() })
                            CreatedDate -> result.copy(data = notes.sortedBy { it.createdAt })
                            ModificationDate -> result.copy(data = notes.sortedBy { it.updatedAt })
                        }
                    }
                    Descending -> {
                        when (noteOrder.criteria) {
                            Title -> result.copy(data = notes.sortedByDescending { it.title.lowercase() })
                            CreatedDate -> result.copy(data = notes.sortedByDescending { it.createdAt })
                            ModificationDate -> result.copy(data = notes.sortedByDescending { it.updatedAt })
                        }
                    }
                }
            }
            else result
        }

}