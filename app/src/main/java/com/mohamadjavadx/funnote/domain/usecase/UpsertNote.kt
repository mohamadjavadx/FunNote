package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Markdown
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.Result
import com.mohamadjavadx.funnote.domain.util.UNKNOWN_ERROR
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class UpsertNote
@Inject
constructor(
    private val repository: NoteRepository,
    private val validateNoteOrThrow: ValidateNoteOrThrow,
) {
    operator fun invoke(note: Note): Flow<Result<Unit>> = try {
        validateNoteOrThrow(note).let { validNote ->
            repository.upsertNote(validNote)
        }
    } catch (exception: Exception) {
        flow {
            emit(
                Result.Error(Message.Snackbar(Markdown(exception.message ?: UNKNOWN_ERROR)))
            )
        }
    }
}