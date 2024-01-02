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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mikohelper.domain.util.NavigationUtil
import com.example.mikohelper.domain.util.NavigationUtil.Directions.ARG_CHAT_ITEM_ID
import com.example.mikohelper.domain.util.NavigationUtil.Directions.CHAT_SCREEN
import com.example.mikohelper.domain.util.NavigationUtil.Directions.HOME_SCREEN
import com.example.mikohelper.domain.util.NavigationUtil.Directions.NEW_CHAT_SCREEN
import com.example.mikohelper.presentation.ui.chat_screen.ChatScreen
import com.example.mikohelper.presentation.ui.home_screen.HomeScreen
import com.example.mikohelper.presentation.ui.new_chat_screen.NewChatScreen
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
                        NavHost(navController = navController, startDestination = HOME_SCREEN) {
                            composable(HOME_SCREEN) {
                                val shouldRefresh = it.savedStateHandle.get<Boolean>(NavigationUtil.Results.REFRESH_NEEDED) ?: false
                                HomeScreen(navController, shouldRefresh)
                            }
                            composable(NEW_CHAT_SCREEN) { NewChatScreen(navController) }
                            composable(
                                route = "$CHAT_SCREEN/{$ARG_CHAT_ITEM_ID}",
                                arguments = listOf(navArgument(ARG_CHAT_ITEM_ID) {type = NavType.IntType})
                            ){ backStackEntry ->
                                val chatItemId = backStackEntry.arguments?.getInt(ARG_CHAT_ITEM_ID)
                                ChatScreen(chatItemId!!, navController)
                            }
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