package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.Result
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetNote
@Inject
constructor(
    private val repository: NoteRepository,
) {
    operator fun invoke(id: Long): Flow<Result<Note>> =
        repository.getNoteStream(id)
}