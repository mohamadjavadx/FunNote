package com.mohamadjavadx.funnote.domain.model

import java.util.*

sealed class Message(
    val id: Long = UUID.randomUUID().mostSignificantBits,
) {
    data class Dialog(
        val messageBody: String,
        val positiveAction: Action? = null,
        val negativeAction: Action? = null,
        val onDismiss: (() -> Unit)? = null,
    ) : Message()

    data class Snackbar(
        val messageBody: String,
        val action: Action? = null,
    ) : Message()

    data class Log(
        val messageBody: String,
    ) : Message()

    interface Action {
        val buttonText: String
        val onAction: () -> Unit
    }
}

