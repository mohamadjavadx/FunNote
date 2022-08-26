package com.mohamadjavadx.funnote.ui.allnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamadjavadx.funnote.domain.model.Markdown
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.usecase.DeleteNote
import com.mohamadjavadx.funnote.domain.usecase.GetNotesStream
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class AllNotesViewModel
@Inject
constructor(
    private val getNotesStream: GetNotesStream,
    private val deleteNote: DeleteNote,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllNotesUiState(isLoading = true))
    val uiState: StateFlow<AllNotesUiState> = _uiState

    init {
        onEvent(AllNotesEvents.Refresh)
    }

    fun onEvent(event: AllNotesEvents) {
        when (event) {
            is AllNotesEvents.UpdateOrder -> changeViewOrder(event.order)
            is AllNotesEvents.DeleteNote -> deleteNote(event.id)
            is AllNotesEvents.MessageShown -> removeShownMassage(event.message)
            is AllNotesEvents.Refresh -> getNotes()
        }
    }

    private fun getNotes() {
        getNotesStream.invoke(_uiState.value.noteOrder)
            .onEach { result ->
                when (result) {
                    is Result.Error -> {
                        _uiState.update {
                            val messages = it.messages + result.message
                            it.copy(isLoading = false, messages = messages)
                        }
                    }
                    is Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Result.Success -> {
                        _uiState.update {
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
        _uiState.update {
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
                        _uiState.update {
                            val messages = it.messages + result.message
                            it.copy(isLoading = false, messages = messages)
                        }
                    }
                    is Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Result.Success -> Unit
                }
            }
            .launchIn(viewModelScope)
    }


    private fun removeShownMassage(message: Message) {
        val messages = _uiState.value.messages.filterNot { it == message }
        _uiState.update {
            it.copy(messages = messages)
        }
    }

}