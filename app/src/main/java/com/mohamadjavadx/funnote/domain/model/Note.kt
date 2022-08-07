package com.mohamadjavadx.funnote.domain.model

import kotlinx.datetime.Instant

data class Note(
    val id: Long,
    val title: String,
    val content: Markdown,
    val createdAt: Instant,
    val updatedAt: Instant,
)
