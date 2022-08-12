package com.mohamadjavadx.funnote.di

import com.mohamadjavadx.funnote.data.repository.NoteRepositoryImpl
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository
}