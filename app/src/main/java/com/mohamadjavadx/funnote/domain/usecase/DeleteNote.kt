package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.NoteID
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.Result
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class DeleteNote
@Inject
constructor(
    private val repository: NoteRepository,
) {
    operator fun invoke(id: NoteID): Flow<Result<Unit>> =
        repository.deleteNote(id)
}