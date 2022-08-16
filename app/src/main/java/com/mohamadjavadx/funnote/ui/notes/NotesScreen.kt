package com.mohamadjavadx.funnote.ui.notes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination(start = true)
@Composable
fun NotesScreen() {
    val viewModel: NotesViewModel = hiltViewModel()

    val uiState by viewModel.viewState.collectAsState()
    NotesScreenContent(
        uiState = uiState,
        onTriggerEvent = viewModel::onEvent
    )
}

@Composable
fun NotesScreenContent(
    uiState: NotesViewState,
    onTriggerEvent: (NotesEvents) -> Unit,
) {



    Text(text = "best developer ever!")
}