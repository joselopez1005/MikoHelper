package com.example.mikohelper.presentation.ui.new_chat_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.util.NavigationUtil.Directions.CHAT_SCREEN
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
        viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewChatScreenContent(
    navController: NavController,
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
            Row (
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProfilePersonalityCard(
                    name = "Akeshi",
                    personality = "mean",
                    modifier = Modifier.clickable {
                        onEvent(NewChatEvent.OnCreateChat(
                            selectedChat = ChatItem(0, "Morgana", "Motivated", R.drawable.ic_profile_akeshi),
                            navigate = {chatId ->
                                navController.navigate("$CHAT_SCREEN/$chatId")
                            }
                        ))
                    }
                )
                ProfilePersonalityCard(name = "Akeshi", personality = "mean")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NewChatScreenContentPreview() {
    MikoHelperTheme {
        val navController = rememberNavController()
        NewChatScreenContent(navController = navController, {})
    }
}