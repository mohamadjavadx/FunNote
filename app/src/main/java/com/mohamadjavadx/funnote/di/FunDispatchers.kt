package com.mohamadjavadx.funnote.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val funDispatcher: FunDispatchers)

enum class FunDispatchers {
    IO
}