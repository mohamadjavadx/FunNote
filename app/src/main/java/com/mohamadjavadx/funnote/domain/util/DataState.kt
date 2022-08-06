package com.mohamadjavadx.funnote.domain.util

import com.mohamadjavadx.funnote.domain.model.Message

data class DataState<T>(
    val message: Message?,
    val data: T?,
    val isLoading: Boolean
) {
    companion object {

        fun <T> error(
            message: Message,
        ): DataState<T> = DataState(message = message, data = null, isLoading = false)

        fun <T> data(
            data: T,
            message: Message? = null,
        ): DataState<T> = DataState(message = message, data = data, isLoading = false)

        fun <T> loading(
            data: T? = null
        ): DataState<T> = DataState(message = null, data = data, isLoading = true)
    }
}