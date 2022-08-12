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
            is NoteListEvents.ChangeViewOrder -> changeViewOrder(event.noteOrder)
            is NoteListEvents.DeleteNotes -> deleteNotes(event.noteIds)
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
            .flowOn(viewModelScope.coroutineContext)

    }

    private fun changeViewOrder(noteOrder: NoteOrder) {
        _viewState.update {
            it.copy(noteOrder = noteOrder).also {
                getNotes()
            }
        }
    }

    private fun deleteNotes(noteIds: List<Long>) {
        deleteNotes.invoke(noteIds)
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
            .flowOn(viewModelScope.coroutineContext)
    }


    private fun removeShownMassage(message: Message) {
        _viewState.update {
            val messages = it.messages - message
            it.copy(messages = messages)
        }
    }

}