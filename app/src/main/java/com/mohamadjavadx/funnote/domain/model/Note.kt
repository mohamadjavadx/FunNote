package com.mohamadjavadx.funnote.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    @Embedded
    val content: Markdown,
    val createdAt: Long,
    val updatedAt: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
)
