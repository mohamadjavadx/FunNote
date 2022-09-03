package com.mohamadjavadx.funnote.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.model.NoteID
import kotlinx.datetime.LocalDate

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: NoteID? = null,
    val title: String,
    val isCompleted: Boolean,
    val scheduledFor: LocalDate,
)

fun NoteEntity.asExternalModel() = Note(
    id = id ?: 0,
    title = title,
    isCompleted = isCompleted,
    scheduledFor = scheduledFor,
)

fun Note.asEntity() = NoteEntity(
    id = id,
    title = title,
    isCompleted = isCompleted,
    scheduledFor = scheduledFor,
)
