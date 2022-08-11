package com.mohamadjavadx.funnote.domain.util

import com.mohamadjavadx.funnote.domain.model.Markdown
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.util.Result.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val message: Message) : Result<Nothing>
    object Loading : Result<Nothing>
//    class Loading<T>(val data: T?) : Result<T>
}

fun <T> Flow<T>.asResult(
    buildExceptionMessage: ((exception: Throwable) -> Message) = {
        Message.Log(Markdown(it.message ?: UNKNOWN_ERROR))
    }
): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Success(it)
        }
        .onStart { emit(Loading) }
        .catch { exception ->
            emit(Error(buildExceptionMessage(exception)))
        }
}