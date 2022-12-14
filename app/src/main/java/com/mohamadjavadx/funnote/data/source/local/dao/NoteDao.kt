package com.mohamadjavadx.funnote.data.source.local.dao

import androidx.room.*
import com.mohamadjavadx.funnote.data.source.local.model.NoteEntity
import com.mohamadjavadx.funnote.domain.model.NoteID
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    fun getNoteStream(id: NoteID): Flow<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE scheduledFor = :scheduledFor")
    fun getNotesStream(scheduledFor: LocalDate): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity")
    fun getNotesStream(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreNote(note: NoteEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreNotes(notes: List<NoteEntity>): List<Long>

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Update
    suspend fun updateNotes(notes: List<NoteEntity>)

    @Transaction
    suspend fun upsertNote(note: NoteEntity): Unit = upsertItem(
        item = note,
        insertOne = ::insertOrIgnoreNote,
        updateOne = ::updateNote
    )

    @Transaction
    suspend fun upsertNotes(notes: List<NoteEntity>): Unit = upsertItems(
        items = notes,
        insertMany = ::insertOrIgnoreNotes,
        updateMany = ::updateNotes
    )

    @Query("DELETE FROM NoteEntity WHERE id = :id")
    suspend fun deleteNote(id: NoteID)

    @Query("DELETE FROM NoteEntity WHERE id in (:ids)")
    suspend fun deleteNotes(ids: List<NoteID>)
}