package com.example.mikohelper.presentation.ui.new_chat_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
    if (viewModel.state.value.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f))
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
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
                onNavIconPressed = { navController.popBackStack() },
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
                columns = GridCells.Adaptive(150.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.value.listOfCharacters.size) { character ->
                    ProfilePersonalityCard(
                        name = state.value.listOfCharacters[character].recipientName,
                        personality = state.value.listOfCharacters[character].personality.mapPersonalityToBriefDescription(),
                        onEvent = {
                            onEvent(NewChatEvent.OnCreateChat(
                                selectedChat = state.value.listOfCharacters[character],
                                navigate = { chatId ->
                                    navController.navigate("$CHAT_SCREEN/$chatId") {
                                        popUpTo(NEW_CHAT_SCREEN) {
                                            inclusive = true
                                        }
                                    }
                                }
                            ))
                        },
                        recipientPicture = state.value.listOfCharacters[character].profilePictureRef,
                        modifier = modifier
                            .padding(8.dp)
                            .height(330.dp)
                    )
                }
            }
        }
    }
}

private fun String.mapPersonalityToBriefDescription(): String {
    if (this == NewChatStates.Personalities.helpfulAssitant) {
        return "Reliable, responsive, empathetic: your helpful assistant."
    }
    if (this == NewChatStates.Personalities.storyTeller) {
        return "Masterful storyteller, captivating hearts with tales."
    }
    if (this == NewChatStates.Personalities.debater) {
        return "Refining rhetoric, molding minds, crafting compelling speakers."
    }
    if (this == NewChatStates.Personalities.therapist) {
        return "Compassionate guide, healing hearts, fostering growth, nurturing mental well-being."
    }
    if (this == NewChatStates.Personalities.socrat) {
        return "Wise questioner, igniting dialogue, provoking critical thought, unraveling insights."
    }
    if (this == NewChatStates.Personalities.wikipedia) {
        return "Condensing knowledge, distilling insights, simplifying information, facilitating quick understanding."
    }
    if (this == NewChatStates.Personalities.timeTraveler) {
        return "Describing time traveler's experiences, recounting historical shifts, revealing nuanced perspectives, storytelling across eras."
    }
    return "Helpful Assistant"
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