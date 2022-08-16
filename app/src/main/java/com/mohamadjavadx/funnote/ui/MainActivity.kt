package com.mohamadjavadx.funnote.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mohamadjavadx.funnote.ui.notes.NavGraph
import com.mohamadjavadx.funnote.ui.notes.NavGraphs
import com.mohamadjavadx.funnote.ui.theme.FunNoteTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunNoteTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}