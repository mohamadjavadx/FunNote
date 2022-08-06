package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository

class UpsertNote(
    private val repository: NoteRepository,
) {
    operator fun invoke(note: Note){

    }
}