package com.example.jetrally

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import com.example.jetrally.ui.components.RallyTopAppBar
import com.example.jetrally.ui.theme.RallyTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        val allScreen = RallyScreen.values().toList()
        var currentScreen by savedInstanceState { RallyScreen.Overview }
        Scaffold(topBar = {
            RallyTopAppBar(
                allScreens = allScreen,
                onTabSelected = { screen -> currentScreen = screen },
                currentScreen = currentScreen
            )
        }) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                currentScreen.content(onScreenChange = { rallyScreen ->
                    currentScreen = rallyScreen
                })
            }

        }
    }
}
