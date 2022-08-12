package com.mohamadjavadx.funnote.ui.notelist

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
class NoteListViewModel
@Inject
constructor(
    private val getNotesStream: GetNotesStream,
    private val deleteNotes: DeleteNotes,
) : ViewModel() {

    private val _viewState: MutableStateFlow<NoteListViewState> =
        MutableStateFlow(NoteListViewState())
    val viewState: StateFlow<NoteListViewState> = _viewState

    init {
        onEvent(NoteListEvents.Refresh)
    }

    fun onEvent(event: NoteListEvents) {
        when (event) {
            is NoteListEvents.ChangeViewOrder -> changeViewOrder(event.order)
            is NoteListEvents.DeleteSelectedNotes -> deleteSelectedNotes()
            is NoteListEvents.SelectNote -> selectNote(event.id)
            is NoteListEvents.DeselectNote -> deselectNote(event.id)
            is NoteListEvents.MessageShown -> removeShownMassage(event.message)
            is NoteListEvents.NavigateToCreateNewNote -> TODO()
            is NoteListEvents.NavigateToNoteDetail -> TODO()
            is NoteListEvents.Refresh -> getNotes()
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

    private fun selectNote(id:Long){
        val selectedNotes = _viewState.value.selectedNotes + id
        _viewState.update {
            it.copy(selectedNotes = selectedNotes)
        }
    }

    private fun deselectNote(id:Long){
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