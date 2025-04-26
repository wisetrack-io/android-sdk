package io.wisetrack.wisetrackapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.wisetrack.wisetrackapp.ui.theme.WiseTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = remember { mutableStateOf(false) }
            WiseTrackTheme(darkTheme = isDarkTheme.value) {
                HomeScreen(
                    onToggleTheme = { isDarkTheme.value = !isDarkTheme.value }
                )
            }
        }
    }
}