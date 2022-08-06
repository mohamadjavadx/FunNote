package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.ERROR_TITLE_BLANK_FIELD
import com.mohamadjavadx.funnote.domain.util.InvalidNoteException

class ValidateNoteOrThrow {
    operator fun invoke(note: Note): Note {
        if (note.title.isBlank()) {
            throw InvalidNoteException(ERROR_TITLE_BLANK_FIELD)
        }
        return note
    }
}