package com.mohamadjavadx.funnote.ui.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohamadjavadx.funnote.ui.components.FunIcon
import com.mohamadjavadx.funnote.ui.components.FunIcons
import com.mohamadjavadx.funnote.ui.components.FunSmallTopAppBar
import com.mohamadjavadx.funnote.ui.navigation.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination


@HomeNavGraph(start = true)
@Destination
@Composable
fun NotesScreen() {
    val viewModel: NotesViewModel = hiltViewModel()

    val uiState by viewModel.viewState.collectAsState()
    NotesScreenContent(
        uiState = uiState,
        onTriggerEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreenContent(
    uiState: NotesViewState,
    onTriggerEvent: (NotesEvents) -> Unit,
) {

    Scaffold(
        topBar = {
            FunSmallTopAppBar(
                screenTitle = "Notes",
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        FunIcon(FunIcons.Sort)
                    }
                }
            )
        },
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Text(text = "best developer ever!")
        }
    }

}