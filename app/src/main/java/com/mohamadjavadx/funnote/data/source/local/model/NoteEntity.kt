package com.mohamadjavadx.funnote.data.source.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohamadjavadx.funnote.domain.model.Note
import kotlinx.datetime.Instant

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    @Embedded
    val content: MarkdownEntity,
    val createdAt: Instant,
    val modifiedAt: Instant,
)

fun NoteEntity.asExternalModel() = Note(
    id = id,
    title = title,
    content = content.asExternalModel(),
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)

fun Note.asEntity() = NoteEntity(
    id = id,
    title = title,
    content = content.asEntity(),
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)
