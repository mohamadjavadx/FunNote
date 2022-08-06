package com.mohamadjavadx.funnote.domain.model

import kotlinx.datetime.Instant
import java.util.*

data class Note(
    val id: UUID,
    val title: String,
    val content: Markdown,
    val createdAt: Instant,
    val updatedAt: Instant,
)
