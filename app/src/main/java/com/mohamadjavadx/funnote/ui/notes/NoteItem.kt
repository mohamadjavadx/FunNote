package com.mohamadjavadx.funnote.ui.notes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.ui.components.FunIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp
) {
    Card(
        modifier = modifier
            .padding(cornerRadius)
            .fillMaxSize(),
        shape = NoteShape(),
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (title, menuButton, menuIcon) = createRefs()

            val topGuideline = createGuidelineFromTop(cornerRadius)
            val startGuideline = createGuidelineFromStart(cornerRadius)
            val endGuideline = createGuidelineFromEnd(cornerRadius)

            FilledIconButton(
                onClick = {},
                shape = NoteShape.NoteMenuShape(),
                modifier = Modifier
                    .constrainAs(menuButton) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        width = Dimension.value(NoteShape.noteMenuSizeDp)
                        height = Dimension.value(NoteShape.noteMenuSizeDp)

                    },
                ) {
                FunIcon(
                    Icons.Default.ExpandMore,
                    Modifier.rotate(45f),
                )
            }

            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(topGuideline)
                    start.linkTo(startGuideline)
                    end.linkTo(menuButton.start, margin = cornerRadius)
                    width = Dimension.fillToConstraints
                },
                text = note.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


        }
    }

}