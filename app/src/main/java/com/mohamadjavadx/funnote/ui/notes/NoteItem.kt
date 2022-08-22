package com.mohamadjavadx.funnote.ui.notes

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mohamadjavadx.funnote.domain.model.Note
import com.mohamadjavadx.funnote.domain.util.toDateTimeString
import com.mohamadjavadx.funnote.ui.components.FunIcon
import com.mohamadjavadx.funnote.ui.components.border

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
) {
    val cornerRadiusPx = with(LocalDensity.current) { cornerRadius.toPx() }
    val menuSize = 48.dp
    val menuSizePX = with(LocalDensity.current) { menuSize.toPx() }
    val contentPadding = 16.dp

    Card(
        modifier = modifier
            .padding(cornerRadius)
            .fillMaxWidth()
            .aspectRatio(2f),
        shape = NoteShape(cornerRadiusPx, menuSizePX),
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (title, menuButton, body, timestamp) = createRefs()

            val topGuideline = createGuidelineFromTop(contentPadding)
            val buttonGuideline = createGuidelineFromBottom(contentPadding)
            val startGuideline = createGuidelineFromStart(contentPadding)
            val endGuideline = createGuidelineFromEnd(contentPadding)

            FilledIconButton(
                onClick = {},
                shape = NoteShape.NoteMenuShape(cornerRadiusPx),
                modifier = Modifier
                    .constrainAs(menuButton) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        width = Dimension.value(menuSize)
                        height = Dimension.value(menuSize)
                    }
                    .border(),
            ) {
                FunIcon(
                    Icons.Default.ExpandMore,
                    Modifier.rotate(45f),
                )
            }

            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(topGuideline)
                        start.linkTo(startGuideline)
                        end.linkTo(menuButton.start, margin = contentPadding)
                        width = Dimension.fillToConstraints
                    }
                    .border(),
                text = note.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(title.bottom, contentPadding)
                        bottom.linkTo(timestamp.top, contentPadding)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .border(),
                text = note.content.originalContent,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .constrainAs(timestamp) {
                        bottom.linkTo(buttonGuideline)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                    .border(),
                text = note.createdAt.toDateTimeString(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


        }
    }
}

class NoteShape(
    private val noteCornerRadius: Float,
    private val noteCutCornerSize: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(drawNotePath(size, noteCornerRadius, noteCutCornerSize))
    }

    private fun drawNotePath(size: Size, cornerRadius: Float, cutCornerSize: Float): Path =
        Path().apply {
            reset()
            //top-left corner
            arcTo(
                rect = Rect(
                    center = Offset(cornerRadius, cornerRadius),
                    radius = cornerRadius
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            //top-right cut corner
            lineTo(size.width - cutCornerSize, 0f)
            lineTo(size.width, cutCornerSize)
            //button-right corner
            lineTo(size.width, size.height - cornerRadius)
            arcTo(
                rect = Rect(
                    center = Offset(size.width - cornerRadius, size.height - cornerRadius),
                    radius = cornerRadius
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            //button-left corner
            lineTo(cornerRadius, size.height)
            arcTo(
                rect = Rect(
                    center = Offset(cornerRadius, size.height - cornerRadius),
                    radius = cornerRadius
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f, cornerRadius)
            close()
        }

    class NoteMenuShape(
        private val noteCornerRadius: Float,
    ) : Shape {

        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            return Outline.Generic(drawNoteMenuPath(size, noteCornerRadius))
        }

        private fun drawNoteMenuPath(size: Size, noteCornerRadius: Float): Path = Path().apply {
            reset()

            val menuCornerRadius = noteCornerRadius * 0.9f
            val startOffset = Offset(size.width * 0.1f, size.height * 0.1f)
            val height = size.height * 0.9f
            val width = size.width * 0.9f

            moveTo(startOffset.x, startOffset.y)
            lineTo(width, height)
            lineTo(menuCornerRadius, height)
            arcTo(
                rect = Rect(
                    center = Offset(menuCornerRadius + startOffset.x, height - menuCornerRadius),
                    radius = menuCornerRadius
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(startOffset.x, startOffset.y)

            close()
        }

    }

}