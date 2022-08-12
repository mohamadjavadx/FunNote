package com.mohamadjavadx.funnote.domain.repository

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNoteStream(id: Long): Flow<Result<Note>>
    fun getNotesStream(): Flow<Result<List<Note>>>
    fun upsertNote(note: Note): Flow<Result<Unit>>
    fun deleteNote(id: Long): Flow<Result<Unit>>
    fun deleteNotes(ids: List<Long>): Flow<Result<Unit>>
}