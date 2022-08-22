package com.mohamadjavadx.funnote.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohamadjavadx.funnote.domain.model.Markdown
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.ui.components.FunIcon
import com.mohamadjavadx.funnote.ui.components.FunIcons
import com.mohamadjavadx.funnote.ui.components.FunSmallTopAppBar
import com.mohamadjavadx.funnote.ui.navigation.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.datetime.Clock
import kotlin.math.sqrt

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

            NoteItem(
                Note(
                    0,
                    "title title title title title title title title title title title title",
                    Markdown("content"),
                    Clock.System.now(),
                    Clock.System.now(),
                )
            )
        }
    }

}