package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Note

class ValidateNoteOrThrow {
    operator fun invoke(note: Note) {
        if (note.title.isBlank()){
            throw
        }
    }
}