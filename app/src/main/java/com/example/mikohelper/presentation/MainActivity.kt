package com.example.mikohelper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mikohelper.presentation.ui.chat_screen.ChatScreen
import com.example.mikohelper.presentation.ui.home_screen.HomeScreen
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(
            ComposeView(this).apply {
                consumeWindowInsets = false
                setContent {

                    MikoHelperTheme {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "homescreen") {
                            composable("homescreen") { HomeScreen(navController) }
                            composable("chatscreen") { ChatScreen() }
                        }
                    }
                }
            }

        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MikoHelperTheme {
        Greeting("Android")
    }
}