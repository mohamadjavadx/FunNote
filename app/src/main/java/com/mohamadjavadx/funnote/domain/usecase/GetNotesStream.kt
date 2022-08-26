package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.OrderType.Ascending
import com.mohamadjavadx.funnote.domain.util.OrderType.Descending
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.NoteOrder.Criteria.*
import com.mohamadjavadx.funnote.domain.util.Result
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class GetNotesStream
@Inject
constructor(
    private val repository: NoteRepository,
) {
    operator fun invoke(noteOrder: NoteOrder): Flow<Result<List<Note>>> =
        repository.getNotesStream().map { result ->
            if (result is Result.Success) {
                val notes = result.data
                when (noteOrder.orderType) {
                    Ascending -> {
                        when (noteOrder.criteria) {
                            Title -> result.copy(data = notes.sortedBy { it.title.lowercase() })
                            DateCreated -> result.copy(data = notes.sortedBy { it.createdAt })
                            DateModified -> result.copy(data = notes.sortedBy { it.modifiedAt })
                        }
                    }
                    Descending -> {
                        when (noteOrder.criteria) {
                            Title -> result.copy(data = notes.sortedByDescending { it.title.lowercase() })
                            DateCreated -> result.copy(data = notes.sortedByDescending { it.createdAt })
                            DateModified -> result.copy(data = notes.sortedByDescending { it.modifiedAt })
                        }
                    }
                }
            } else result
        }

}