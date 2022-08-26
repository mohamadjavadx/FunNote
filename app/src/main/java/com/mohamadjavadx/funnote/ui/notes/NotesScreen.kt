package com.mohamadjavadx.funnote.ui.notes

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohamadjavadx.funnote.domain.model.Markdown
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.NoteOrder.Criteria
import com.mohamadjavadx.funnote.domain.util.readableName
import com.mohamadjavadx.funnote.ui.components.*
import com.mohamadjavadx.funnote.ui.navigation.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.datetime.Clock

@HomeNavGraph(start = true)
@Destination
@Composable
fun NotesScreen() {
    val viewModel: NotesViewModel = hiltViewModel()

    val state by viewModel.viewState.collectAsState()
    NotesScreenContent(
        orderCriteria = state.noteOrder.criteria,
        orderArrangement = state.noteOrder.orderType,
        notes = state.notes,
        isLoading = state.isLoading,
        messages = state.messages,
        onTriggerEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreenContent(
    orderCriteria: Criteria,
    orderArrangement: com.mohamadjavadx.funnote.domain.util.OrderType,
    notes:List<Note>,
    isLoading:Boolean,
    messages: List<Message>,
    onTriggerEvent: (NotesEvents) -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            FunSmallTopAppBar(
                screenTitle = "All Notes",
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        FunIcon(imageVector = Icons.Default.Search)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { contentPadding: PaddingValues ->
        val notes: List<Note> = remember {
            List(100) {
                Note(
                    it + 1.toLong(),
                    "title $it",
                    Markdown("content"),
                    Clock.System.now(),
                    Clock.System.now(),
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            item(key = "OrderView") {
                OrderView(orderCriteria = readableName(orderCriteria))
            }

            items(items = notes, key = { it.id }) { note ->
                NoteItem(
                    note = note,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }
}

@Composable
private fun OrderView(
    orderCriteria: String,
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Row(
            modifier = Modifier.clickable {
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            FunIcon(
                funIcon = FunIcons.Sort,
                modifier = Modifier.height(with(LocalDensity.current) {
                    MaterialTheme.typography.labelLarge.lineHeight.toDp()
                }),
            )
            Text(
                text = orderCriteria,
                style = MaterialTheme.typography.labelLarge
            )
        }

        FunVerticalDivider(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(with(LocalDensity.current) {
                MaterialTheme.typography.labelLarge.fontSize.toDp()
            })
        )

        orderCriteria

        FunIcon(
            imageVector = Icons.Default.ArrowUpward,
            modifier = Modifier
                .height(with(LocalDensity.current) {
                    MaterialTheme.typography.labelLarge.lineHeight.toDp()
                })
                .clickable {
                }
        )
    }

}