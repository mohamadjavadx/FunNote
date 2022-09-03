package com.mohamadjavadx.funnote.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohamadjavadx.funnote.domain.model.Message
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.toLocalDate
import com.mohamadjavadx.funnote.domain.util.toLocalDateString
import com.mohamadjavadx.funnote.ui.components.FunIcon
import com.mohamadjavadx.funnote.ui.navigation.HomeNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlinx.datetime.*

@HomeNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel: HomeViewModel = hiltViewModel()

    val state by viewModel.uiState.collectAsState()
    HomeScreenContent(
        selectedDate = state.selectedDate,
        notes = state.visibleNotes,
        draftNote = state.draftNote,
        isLoading = state.isLoading,
        messages = state.messages,
        onTriggerEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    selectedDate: Instant,
    notes: List<Note>,
    draftNote: DraftNote,
    isLoading: Boolean,
    messages: List<Message>,
    onTriggerEvent: (HomeEvents) -> Unit,
) {

    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(state = topBarState)
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val now = Clock.System.now()
                        IconButton(onClick = {
                            onTriggerEvent(HomeEvents.SelectDate(selectedDate.minus(1, DateTimeUnit.DAY,
                                TimeZone.currentSystemDefault())))
                        }) {
                            FunIcon(imageVector = Icons.Default.ArrowLeft)
                        }
                        val title: String = when (selectedDate.toLocalDate()) {
                            now.toLocalDate() -> {
                                "Today"
                            }
                            else -> {
                                selectedDate.toLocalDateString()
                            }
                        }
                        Text(text = title)
                        IconButton(onClick = {
                            onTriggerEvent(HomeEvents.SelectDate(selectedDate.plus(1, DateTimeUnit.DAY,
                                TimeZone.currentSystemDefault())))
                        }) {
                            FunIcon(imageVector = Icons.Default.ArrowRight)
                        }
                    }
                }
            )
        },
    ) { contentPadding: PaddingValues ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
        ) {

            Column(modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = scrollState,
                    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(items = notes, key = { it.id ?: throw Throwable() }) { note ->
                        NoteItem(
                            note = note,
                            toggleCompletion = {
                                onTriggerEvent(
                                    HomeEvents.ToggleNoteCompletionStatus(note.id
                                        ?: throw Throwable())
                                )
                            },
                            toggleDelete = {
                                onTriggerEvent(
                                    HomeEvents.DeleteNote(note.id
                                        ?: throw Throwable())
                                )
                            }
                        )
                    }
                }

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        TextField(
//                            modifier = Modifier.weight(1f),
//                            value = draftNote.title,
//                            onValueChange = {
//                                onTriggerEvent(HomeEvents.DraftNote(it))
//                            }
//                        )


                        var textState by remember {
                            mutableStateOf(TextFieldValue(text = draftNote.title))
                        }

                        // Used to decide if the keyboard should be shown
                        var textFieldFocusState by remember { mutableStateOf(false) }

                        UserInputText(
                            modifier = Modifier.weight(1f),
                            onValueChange = {
                                onTriggerEvent(HomeEvents.DraftNote(it))
                            },
                            value = draftNote.title,
                            // Close extended selector if text field receives focus
                            onTextFieldFocused = { focused ->
                                if (focused) {
                                    scope.launch {
                                        scrollState.scrollToItem(notes.size)
                                    }
                                }
                                textFieldFocusState = focused
                            },
                            focusState = textFieldFocusState
                        )

                        Spacer(modifier = Modifier.width(16.dp))
                        CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                            Button(
                                modifier = Modifier
                                    .defaultMinSize(minWidth = 30.dp, minHeight = 30.dp),
                                contentPadding = PaddingValues(8.dp),
                                onClick = { onTriggerEvent(HomeEvents.AddNote) },
                                enabled = draftNote.title.isNotBlank(),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                FunIcon(imageVector = Icons.Default.Add)
                            }
                        }
                    }
                }

            }

        }
    }
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey

@ExperimentalFoundationApi
@Composable
private fun UserInputText(
    onValueChange: (String) -> Unit,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean,
    modifier: Modifier,
) {

    // Holds the latest internal TextFieldValue state. We need to keep it to have the correct value
    // of the composition.
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    // Holds the latest TextFieldValue that BasicTextField was recomposed with. We couldn't simply
    // pass `TextFieldValue(text = value)` to the CoreTextField because we need to preserve the
    // composition.
    val textFieldValue = textFieldValueState.copy(text = value)
    // Last String value that either text field was recomposed with or updated in the onValueChange
    // callback. We keep track of it to prevent calling onValueChange(String) for same String when
    // CoreTextField's onValueChange is called multiple times without recomposition in between.
    var lastTextValue by remember(value) { mutableStateOf(value) }

    Surface(modifier = modifier) {
        Box {
            var lastFocusState by remember { mutableStateOf(false) }
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newTextFieldValueState ->
                    textFieldValueState = newTextFieldValueState

                    val stringChangedSinceLastInvocation =
                        lastTextValue != newTextFieldValueState.text
                    lastTextValue = newTextFieldValueState.text

                    if (stringChangedSinceLastInvocation) {
                        onValueChange(newTextFieldValueState.text)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .align(Alignment.CenterStart)
                    .onFocusChanged { state ->
                        if (lastFocusState != state.isFocused) {
                            onTextFieldFocused(state.isFocused)
                        }
                        lastFocusState = state.isFocused
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.Send
                ),
                cursorBrush = SolidColor(LocalContentColor.current),
                textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
            )

            val disableContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            if (textFieldValue.text.isBlank()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    text = "todo..",
                    style = MaterialTheme.typography.bodyLarge.copy(color = disableContentColor)
                )
            }
        }
    }

}