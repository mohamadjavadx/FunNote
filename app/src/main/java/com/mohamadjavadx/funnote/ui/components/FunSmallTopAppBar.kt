package com.mohamadjavadx.funnote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunSmallTopAppBar(
    title: String,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    SmallTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunEditableSmallTopAppBar(
    title: String,
    hint: String,
    onTextChange: (String) -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    SmallTopAppBar(
        title = {
            TextField(
                modifier = Modifier.background(Color.Transparent),
                value = title,
                onValueChange = onTextChange,
                placeholder = {
                    Text(text = hint)
                },
                maxLines = 1,

            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior
    )
}