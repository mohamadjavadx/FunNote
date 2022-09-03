package com.mohamadjavadx.funnote.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.ui.components.FunIcon
import com.mohamadjavadx.funnote.ui.components.border

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NoteItem(note: Note, toggleCompletion: () -> Unit,toggleDelete: () -> Unit) {

    val dismissState = rememberDismissState()

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        toggleDelete()
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> MaterialTheme.colorScheme.surfaceTint
                    DismissValue.DismissedToEnd -> Color.Green
                    DismissValue.DismissedToStart -> MaterialTheme.colorScheme.error
                }
            )
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val icon = when (direction) {
                DismissDirection.StartToEnd -> Icons.Default.Done
                DismissDirection.EndToStart -> Icons.Default.Delete
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.8f else 1f
            )

            Box(
                Modifier.fillMaxSize().background(color).padding(horizontal = 16.dp),
                contentAlignment = alignment
            ) {
                FunIcon(
                    imageVector = icon,
                    modifier = Modifier.scale(scale),
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        },
        dismissContent = {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                elevation = CardDefaults.elevatedCardElevation()
            ) {
                Row(
                    Modifier.padding(16.dp)
                ) {
                    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                        Checkbox(
                            modifier = Modifier
                                .border(),
                            checked = note.isCompleted,
                            onCheckedChange = {
                                toggleCompletion()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .border(),
                        text = note.title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        })



}