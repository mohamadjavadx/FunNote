package com.mohamadjavadx.funnote.di

import android.content.Context
import androidx.room.Room
import com.mohamadjavadx.funnote.data.source.local.LocalDatabase
import com.mohamadjavadx.funnote.data.source.local.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesLocalDatabase(
        @ApplicationContext context: Context,
    ): LocalDatabase = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        LocalDatabase.DatabaseName
    ).build()

    @Provides
    @Singleton
    fun providesAuthorDao(
        database: LocalDatabase,
    ): NoteDao = database.noteDao()

}