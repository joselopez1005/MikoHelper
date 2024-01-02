package com.example.mikohelper.presentation.ui.new_chat_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mikohelper.domain.util.NavigationUtil.Directions.CHAT_SCREEN
import com.example.mikohelper.domain.util.NavigationUtil.Directions.NEW_CHAT_SCREEN
import com.example.mikohelper.presentation.ui.components.MikoHelperAppBar
import com.example.mikohelper.presentation.ui.components.ProfilePersonalityCard
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme

@Composable
fun NewChatScreen(
    navController: NavController,
    viewModel: NewChatScreenViewModel = hiltViewModel()
) {
    NewChatScreenContent(
        navController,
        viewModel.state,
        viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewChatScreenContent(
    navController: NavController,
    state: State<NewChatStates>,
    onEvent: (NewChatEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            MikoHelperAppBar(
                title = { Text(text = "New Chat") },
                onNavIconPressed = {},
                actions = {}
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
        
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            LazyVerticalGrid(
                columns =  GridCells.Adaptive(150.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.value.listOfCharacters.size) { character->
                    ProfilePersonalityCard(
                        name = state.value.listOfCharacters[character].recipientName,
                        personality = state.value.listOfCharacters[character].personality.mapPersonalityToBriefDescription(),
                        onEvent = {
                                  onEvent(NewChatEvent.OnCreateChat(
                                      selectedChat = state.value.listOfCharacters[character],
                                      navigate = {chatId ->
                                        navController.navigate("$CHAT_SCREEN/$chatId") {
                                            popUpTo(NEW_CHAT_SCREEN) {
                                                inclusive = true
                                            }
                                        }
                                      }
                                  ))
                        },
                        modifier = modifier
                            .padding(8.dp)
                            .height(300.dp)
                    )
                }
            }
        }
    }
}

private fun String.mapPersonalityToBriefDescription(): String {
    if (this == NewChatStates.Personalities.homelander) {
        return "Selfish and Rude"
    }
    return  "Helpful Assitant"
}

@Composable
@Preview(showBackground = true)
fun NewChatScreenContentPreview() {
    MikoHelperTheme {
        val navController = rememberNavController()
        val state = remember {
            mutableStateOf(NewChatStates())
        }
        NewChatScreenContent(navController = navController, state = state, {})
    }
}