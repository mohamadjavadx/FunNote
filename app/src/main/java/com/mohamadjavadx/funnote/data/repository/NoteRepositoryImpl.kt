package com.mohamadjavadx.funnote.data.repository

import com.mohamadjavadx.funnote.data.source.local.dao.NoteDao
import com.mohamadjavadx.funnote.data.source.local.model.NoteEntity
import com.mohamadjavadx.funnote.data.source.local.model.asEntity
import com.mohamadjavadx.funnote.data.source.local.model.asExternalModel
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.Result
import com.mohamadjavadx.funnote.domain.util.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao,
) : NoteRepository {

    override fun getNoteStream(id: Long): Flow<Result<Note>> =
        dao.getNoteStream(id).map(NoteEntity::asExternalModel).asResult()

    override fun getNotesStream(): Flow<Result<List<Note>>> =
        dao.getNotesStream().map { it.map(NoteEntity::asExternalModel) }.asResult()

    override fun upsertNote(note: Note): Flow<Result<Unit>> = flow {
        dao.upsertNote(note.asEntity())
    }

    override fun deleteNote(id: Long): Flow<Result<Unit>> = flow {
        emit(dao.deleteNote(id))
    }.asResult()

}