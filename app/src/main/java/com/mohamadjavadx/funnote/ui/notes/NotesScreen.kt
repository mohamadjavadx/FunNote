package com.mohamadjavadx.funnote.ui.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohamadjavadx.funnote.domain.model.Markdown
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.NoteOrder
import com.mohamadjavadx.funnote.domain.util.OrderType
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
        order = state.noteOrder,
        notes = state.notes,
        isLoading = state.isLoading,
        messages = state.messages,
        onTriggerEvent = viewModel::onEvent,
        navigateToEditScreen = { noteId: Long ->

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreenContent(
    order: NoteOrder,
    notes: List<Note>,
    isLoading: Boolean,
    messages: List<Message>,
    onTriggerEvent: (NotesEvents) -> Unit,
    navigateToEditScreen: (Long) -> Unit,
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

        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            item(key = "OrderView") {
                OrderView(
                    order = order,
                    onUpdateOrder = {
                        onTriggerEvent(NotesEvents.UpdateOrder(it))
                    }
                )
            }

            items(items = notes, key = { it.id }) { note ->
                NoteItem(
                    note = note,
                    modifier = Modifier.animateItemPlacement(),
                    toggleEdit = {
                        navigateToEditScreen(note.id)
                    },
                    toggleDelete = {
                        onTriggerEvent(NotesEvents.DeleteNote(note.id))
                    }
                )
            }
        }
    }
}

@Composable
private fun OrderView(
    order: NoteOrder,
    onUpdateOrder: (order: NoteOrder) -> Unit
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
        val lineHeightDp = with(LocalDensity.current) {
            MaterialTheme.typography.labelLarge.lineHeight.toDp()
        }

        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
//                .fillMaxWidth(Alignment.TopEnd)
                .clickable {
                    expanded = true
                }
                .border(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            FunIcon(
                funIcon = FunIcons.Sort,
                modifier = Modifier.height(lineHeightDp),
            )
            Text(
                text = readableName(order.criteria),
                style = MaterialTheme.typography.labelLarge
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, -lineHeightDp),
                modifier = Modifier.border(),
            ) {
                NoteOrder.Criteria.values().forEach {
                    DropdownMenuItem(
                        text = {
                            Text(readableName(it))
                        },
                        onClick = {
                            expanded = false
                            onUpdateOrder(NoteOrder(it, order.orderType))
                        }
                    )
                }
            }
        }

        FunVerticalDivider(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(with(LocalDensity.current) {
                    MaterialTheme.typography.labelLarge.fontSize.toDp()
                })
        )

        FunIcon(
            imageVector = when (order.orderType) {
                OrderType.Ascending -> Icons.Default.ArrowDownward
                OrderType.Descending -> Icons.Default.ArrowUpward
            },
            modifier = Modifier
                .height(lineHeightDp)
                .clickable {
                    onUpdateOrder(
                        NoteOrder(
                            order.criteria, when (order.orderType) {
                                OrderType.Ascending -> OrderType.Descending
                                OrderType.Descending -> OrderType.Ascending
                            }
                        )
                    )
                }
        )
    }

}