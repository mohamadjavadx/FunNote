package com.mohamadjavadx.funnote.domain.repository

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.*

interface NoteRepository {
    fun insertNote(note: Note): Flow<Result<Unit>>
    fun updateNote(note: Note): Flow<Result<Unit>>
    fun upsertNote(note: Note): Flow<Result<Unit>>
    fun getAllNotes(): Flow<Result<List<Note>>>
    fun getNote(id: Long): Flow<Result<Note>>
    fun deleteNote(id: Long): Flow<Result<Unit>>
}