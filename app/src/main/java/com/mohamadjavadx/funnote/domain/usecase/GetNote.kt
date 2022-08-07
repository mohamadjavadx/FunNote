package com.mohamadjavadx.funnote.domain.usecase

import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.repository.NoteRepository
import com.mohamadjavadx.funnote.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import java.util.*

class GetNote(
    private val repository: NoteRepository,
) {
    operator fun invoke(id: Long): Flow<DataState<Note>> =
        repository.getNote(id)
}