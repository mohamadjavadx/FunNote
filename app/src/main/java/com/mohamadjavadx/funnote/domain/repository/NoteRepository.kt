package com.mohamadjavadx.funnote.domain.repository

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import java.util.*

interface NoteRepository {
    fun getAllNotes(): Flow<DataState<List<Note>>>
    fun getNote(id: UUID): Flow<DataState<Note>>
    fun upsertNote(note: Note): Flow<DataState<Unit>>
    fun deleteNote(id: UUID): Flow<DataState<Unit>>
}