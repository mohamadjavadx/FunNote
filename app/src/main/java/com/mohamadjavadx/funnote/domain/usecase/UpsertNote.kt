package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Markdown
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.DataState
import com.mohamadjavadx.funnote.domain.util.UNKNOWN_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpsertNote(
    private val repository: NoteRepository,
    private val validateNoteOrThrow: ValidateNoteOrThrow,
) {
    operator fun invoke(note: Note): Flow<DataState<Unit>> = try {
        validateNoteOrThrow(note).let { validNote ->
            repository.upsertNote(validNote)
        }
    } catch (exception: Exception) {
        flow {
            emit(
                DataState.error(
                    Message.Snackbar(
                        messageBody = Markdown(exception.message ?: UNKNOWN_ERROR)
                    )
                )
            )
        }
    }

}