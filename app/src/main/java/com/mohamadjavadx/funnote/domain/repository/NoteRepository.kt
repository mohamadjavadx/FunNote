package com.mohamadjavadx.funnote.domain.repository

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.model.NoteID
import com.mohamadjavadx.funnote.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface NoteRepository {
    fun getNoteStream(id: NoteID): Flow<Result<Note>>
    fun getNotesStream(scheduledFor: LocalDate): Flow<Result<List<Note>>>
    fun getNotesStream(): Flow<Result<List<Note>>>
    fun upsertNote(note: Note): Flow<Result<Unit>>
    fun deleteNote(id: NoteID): Flow<Result<Unit>>
    fun deleteNotes(ids: List<NoteID>): Flow<Result<Unit>>
}