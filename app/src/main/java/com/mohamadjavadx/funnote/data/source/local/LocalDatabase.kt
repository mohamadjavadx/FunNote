package com.mohamadjavadx.funnote.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohamadjavadx.funnote.data.source.local.dao.NoteDao
import com.mohamadjavadx.funnote.data.source.local.model.NoteEntity
import com.mohamadjavadx.funnote.data.util.InstantConverter
import com.mohamadjavadx.funnote.data.util.LocalDateConverter

@Database(
    entities = [NoteEntity::class],
    version = 1,
)
@TypeConverters(
    InstantConverter::class,
    LocalDateConverter::class,
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DatabaseName = "local_database"
    }
}