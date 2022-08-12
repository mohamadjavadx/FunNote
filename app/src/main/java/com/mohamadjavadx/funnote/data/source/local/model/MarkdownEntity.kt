package com.mohamadjavadx.funnote.data.source.local.model

import com.mohamadjavadx.funnote.domain.model.Markdown

data class MarkdownEntity(
    val originalContent: String,
)

fun MarkdownEntity.asExternalModel() = Markdown(
    originalContent = originalContent
)

fun Markdown.asEntity() = MarkdownEntity(
    originalContent = originalContent
)
