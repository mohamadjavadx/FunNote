package com.mohamadjavadx.funnote.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.mohamadjavadx.funnote.R

enum class FunIcons(val resourceId: Int) {
    Sort(R.drawable.ic_sort)
}

@Composable
fun FunIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(imageVector, contentDescription)
}

@Composable
fun FunIcon(
    funIcon: FunIcons,
    contentDescription: String? = null,
) {
    Icon(painterResource(funIcon.resourceId), contentDescription,)
}