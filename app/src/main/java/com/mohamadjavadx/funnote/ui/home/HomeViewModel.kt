package com.mohamadjavadx.funnote.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.model.NoteID
import com.mohamadjavadx.funnote.domain.usecase.DeleteNote
import com.mohamadjavadx.funnote.domain.usecase.GetNotesStream
import com.mohamadjavadx.funnote.domain.usecase.UpsertNote
import com.mohamadjavadx.funnote.domain.util.Result
import com.mohamadjavadx.funnote.domain.util.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getNotesStream: GetNotesStream,
    private val deleteNote: DeleteNote,
    private val upsertNote: UpsertNote,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    private var getNotesJob: Job? = Job()

    init {
        onEvent(HomeEvents.Refresh)
    }

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.DraftNote -> {
                _uiState.update {
                    it.copy(
                        draftNote = DraftNote(
                            title = event.noteTitle,
                        )
                    )
                }
            }
            is HomeEvents.Refresh -> getNotes()
            is HomeEvents.AddNote -> {
                val title = _uiState.value.draftNote.title
                if (title.isNotBlank()) {
                    upsertNote(
                        Note(
                            title = title,
                            isCompleted = false,
                            scheduledFor = _uiState.value.selectedDate.toLocalDate()
                        )
                    ).also {
                        _uiState.update { it.copy(draftNote = DraftNote()) }
                    }
                }
            }
            is HomeEvents.EditNote -> {
                val note = _uiState.value.visibleNotes.find { it.id == event.id } ?: return
                upsertNote(note.copy(title = event.title))
            }
            is HomeEvents.ToggleNoteCompletionStatus -> {
                val note = _uiState.value.visibleNotes.find { it.id == event.id } ?: return
                upsertNote(note.copy(isCompleted = !note.isCompleted))
            }
            is HomeEvents.DeleteNote -> deleteNote(event.id)
            is HomeEvents.SelectDate -> {
                _uiState.update {
                    _uiState.value.copy(selectedDate = event.date)
                }.also {
                    getNotes()
                }

            }
            is HomeEvents.MessageShown -> removeShownMassage(event.message)
        }
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = getNotesStream.invoke(_uiState.value.selectedDate.toLocalDate())
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
                            it.copy(
                                isLoading = true,
                                visibleNotes = result.data
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun upsertNote(note: Note) {
        upsertNote.invoke(note)
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

    private fun deleteNote(id: NoteID) {
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