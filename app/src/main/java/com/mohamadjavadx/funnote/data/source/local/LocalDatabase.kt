package com.mohamadjavadx.funnote.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohamadjavadx.funnote.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
)
abstract class LocalDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DatabaseName = "local_database"
    }
}