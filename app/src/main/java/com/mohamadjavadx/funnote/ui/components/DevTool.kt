package com.mohamadjavadx.funnote.ui.components

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

fun Modifier.border(
    color: Color = Color(
        red = Random.nextInt(0, 255),
        green = Random.nextInt(0, 255),
        blue = Random.nextInt(0, 255)
    )
) = border(width = 1.dp, color = color) /*this*/