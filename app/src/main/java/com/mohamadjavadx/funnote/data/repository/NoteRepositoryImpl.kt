package com.mohamadjavadx.funnote.data.repository

import com.mohamadjavadx.funnote.data.source.local.NoteDao
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.Result
import com.mohamadjavadx.funnote.domain.util.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(
    private val dao: NoteDao,
) : NoteRepository {

    override fun insertNote(note: Note): Flow<Result<Unit>> = flow {
        dao.insertOrReplace(note)
        emit(Unit)
    }.asResult()

    override fun updateNote(note: Note): Flow<Result<Unit>> = flow {
        dao.update(note)
        emit(Unit)
    }.asResult()

    override fun upsertNote(note: Note): Flow<Result<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getAllNotes(): Flow<Result<List<Note>>> =
        dao.getAll().asResult()

    override fun getNote(id: Long): Flow<Result<Note>> =
        dao.get(id).asResult(
//        buildExceptionMessage = {}
        )


    override fun deleteNote(id: Long): Flow<Result<Unit>> = flow {
        emit(dao.delete(id))
    }.asResult()


}