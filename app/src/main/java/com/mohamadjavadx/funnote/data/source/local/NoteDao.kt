package com.mohamadjavadx.funnote.data.source.local

import androidx.room.*
import com.mohamadjavadx.funnote.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // https://betterprogramming.pub/upserting-in-room-8207a100db53
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(vararg note: Note)

    @Update
    suspend fun update(vararg note: Note)

    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun delete(vararg id: Long)

    @Query("SELECT * FROM Note WHERE id = :id")
    fun get(id: Long): Note?

    @Query("SELECT * FROM Note")
    fun getAll(): Flow<List<Note>>

}