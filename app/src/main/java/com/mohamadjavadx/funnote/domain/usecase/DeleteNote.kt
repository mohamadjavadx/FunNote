package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.Result
import kotlinx.coroutines.flow.Flow

class DeleteNote(
    private val repository: NoteRepository,
) {
    operator fun invoke(id: Long): Flow<Result<Unit>> =
        repository.deleteNote(id)
}