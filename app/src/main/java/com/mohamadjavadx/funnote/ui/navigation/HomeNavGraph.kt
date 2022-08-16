package com.mohamadjavadx.funnote.ui.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@NavGraph
@RootNavGraph(start = true)
annotation class HomeNavGraph(
    val start: Boolean = false
)