package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.Result
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@ViewModelScoped
class GetNotesStream
@Inject
constructor(
    private val repository: NoteRepository,
) {
    operator fun invoke(scheduledFor: LocalDate): Flow<Result<List<Note>>> =
        repository.getNotesStream(scheduledFor)

    operator fun invoke(): Flow<Result<List<Note>>> =
        repository.getNotesStream()

}