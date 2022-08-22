package com.mohamadjavadx.funnote.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.mohamadjavadx.funnote.R

enum class FunIcons(val resourceId: Int) {
    Sort(R.drawable.ic_sort)
}

@Composable
fun FunIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = null,
) {
    Icon(imageVector, contentDescription, modifier,tint)
}

@Composable
fun FunIcon(
    funIcon: FunIcons,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = null,
) {
    Icon(painterResource(funIcon.resourceId), contentDescription, modifier,tint)
}