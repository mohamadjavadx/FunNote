package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import java.util.*

class DeleteNote(
    private val repository: NoteRepository,
) {
    operator fun invoke(id: UUID): Flow<DataState<Unit>> =
        repository.deleteNote(id)
}