package com.mohamadjavadx.funnote.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.usecase.DeleteNotes
import com.mohamadjavadx.funnote.domain.usecase.GetNotesStream
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NotesViewModel
@Inject
constructor(
    private val getNotesStream: GetNotesStream,
    private val deleteNotes: DeleteNotes,
) : ViewModel() {

    private val _viewState = MutableStateFlow(NotesViewState(isLoading = true))
    val viewState: StateFlow<NotesViewState> = _viewState

    init {
        onEvent(NotesEvents.Refresh)
    }

    fun onEvent(event: NotesEvents) {
        when (event) {
            is NotesEvents.ChangeViewOrder -> changeViewOrder(event.order)
            is NotesEvents.DeleteSelectedNotes -> deleteSelectedNotes()
            is NotesEvents.SelectNote -> selectNote(event.id)
            is NotesEvents.DeselectNote -> deselectNote(event.id)
            is NotesEvents.MessageShown -> removeShownMassage(event.message)
            is NotesEvents.NavigateToCreateNewNote -> TODO()
            is NotesEvents.NavigateToNoteDetail -> TODO()
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
                            it.copy(isLoading = true, notes = result.data)
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

    private fun selectNote(id: Long) {
        val selectedNotes = _viewState.value.selectedNotes + id
        _viewState.update {
            it.copy(selectedNotes = selectedNotes)
        }
    }

    private fun deselectNote(id: Long) {
        val selectedNotes = _viewState.value.selectedNotes - id
        _viewState.update {
            it.copy(selectedNotes = selectedNotes)
        }
    }

    private fun deleteSelectedNotes() {
        deleteNotes.invoke(_viewState.value.selectedNotes.toList())
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