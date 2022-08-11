package com.mohamadjavadx.funnote.domain.util

class InvalidNoteException(message: String) : Exception(message)

const val ERROR_TITLE_BLANK_FIELD = "Title field must not be blank."

const val UNKNOWN_ERROR = "Unknown Error"