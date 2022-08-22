package com.mohamadjavadx.funnote.ui.notes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class NoteShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            drawNotePath(
                size,
                with(density) { noteCornerRadiusDp.toPx() },
                with(density) { cutCornerSizeDp.toPx() },
            )
        )
    }

    private fun drawNotePath(
        size: Size,
        cornerRadius: Float,
        cutCornerSize: Float
    ): Path = Path().apply {
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

    class NoteMenuShape : Shape {

        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            return Outline.Generic(
                drawNoteMenuPath(
                    size,
                    with(density) { noteCornerRadiusDp.toPx() })
            )
        }

        private fun drawNoteMenuPath(size: Size, noteCornerRadius: Float): Path = Path().apply {
            reset()
            lineTo(size.width, size.height)
            lineTo(noteCornerRadius, size.height)
            arcTo(
                rect = Rect(
                    center = Offset(noteCornerRadius, size.height - noteCornerRadius),
                    radius = noteCornerRadius
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f, 0f)
            close()
        }

    }

    companion object {
        val noteCornerRadiusDp = 16.dp
        val cutCornerSizeDp = noteCornerRadiusDp * 3
        val noteMenuSizeDp = cutCornerSizeDp
    }

}