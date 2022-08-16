package com.mohamadjavadx.funnote.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunSmallTopAppBar(
    screenTitle: String,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    SmallTopAppBar(
        title = {
            Text(
                text = screenTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
    )
}