package com.mohamadjavadx.funnote.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamadjavadx.funnote.domain.model.Markdown
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.usecase.DeleteNote
import com.mohamadjavadx.funnote.domain.usecase.DeleteNotes
import com.mohamadjavadx.funnote.domain.usecase.GetNotesStream
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class NotesViewModel
@Inject
constructor(
    private val getNotesStream: GetNotesStream,
    private val deleteNote: DeleteNote,
) : ViewModel() {

    private val _viewState = MutableStateFlow(NotesViewState(isLoading = true))
    val viewState: StateFlow<NotesViewState> = _viewState

    init {
        onEvent(NotesEvents.Refresh)
    }

    fun onEvent(event: NotesEvents) {
        when (event) {
            is NotesEvents.UpdateOrder -> changeViewOrder(event.order)
            is NotesEvents.DeleteNote -> deleteNote(event.id)
            is NotesEvents.MessageShown -> removeShownMassage(event.message)
            is NotesEvents.Refresh -> getNotes()
        }
    }

    private fun getNotes() {
        getNotesStream.invoke(_viewState.value.noteOrder)
            .onEach { result ->
                when (result) {
                    is Result.Error -> {
                        _viewState.update {
                            val messages = it.messages + result.message
                            it.copy(isLoading = false, messages = messages)
                        }
                    }
                    is Result.Loading -> {
                        _viewState.update { it.copy(isLoading = true) }
                    }
                    is Result.Success -> {
                        _viewState.update {
                            it.copy(isLoading = true, notes = result.data.plus(
                                List(2) {
                                    Note(
                                        it + 1.toLong(),
                                        "title $it",
                                        Markdown("content"),
                                        Clock.System.now(),
                                        Clock.System.now(),
                                    )
                                }
                            ))
                        }
                    }
                }
            }
            .launchIn(viewModelScope)

    }

    private fun changeViewOrder(noteOrder: NoteOrder) {
        _viewState.update {
            it.copy(noteOrder = noteOrder).also {
                getNotes()
            }
        }
    }

    private fun deleteNote(id:Long) {
        deleteNote.invoke(id)
            .onEach { result ->
                when (result) {
                    is Result.Error -> {
                        _viewState.update {
                            val messages = it.messages + result.message
                            it.copy(isLoading = false, messages = messages)
                        }
                    }
                    is Result.Loading -> {
                        _viewState.update { it.copy(isLoading = true) }
                    }
                    is Result.Success -> Unit
                }
            }
            .launchIn(viewModelScope)
    }


    private fun removeShownMassage(message: Message) {
        val messages = _viewState.value.messages.filterNot { it == message }
        _viewState.update {
            it.copy(messages = messages)
        }
    }

}