package com.example.mikohelper.presentation.ui.home_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mikohelper.domain.util.NavigationUtil.Directions.CHAT_SCREEN
import com.example.mikohelper.domain.util.NavigationUtil.Directions.NEW_CHAT_SCREEN
import com.example.mikohelper.presentation.ui.components.MikoHelperAppBar
import com.example.mikohelper.presentation.ui.components.MikoHelperAppBarNoNavigation
import com.example.mikohelper.presentation.ui.components.ProfileCardRemovingState
import com.example.mikohelper.presentation.ui.components.ProfileCardWithLatestMessageTimeStamp
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme

@Composable
fun HomeScreen(
    navController: NavController,
    shouldRefresh: Boolean = false,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(shouldRefresh) {
        viewModel.onEvent(HomeScreenEvent.OnRefresh)
    }

    BackHandler(viewModel.state.value.isRemovingState) {
        viewModel.onEvent(HomeScreenEvent.OnToggleRemoveState)
    }

    HomeScreenContent(
        navController,
        viewModel::onEvent,
        viewModel.state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    navController: NavController,
    onEvent: (HomeScreenEvent) -> Unit,
    state: State<HomeScreenStates>
) {
    Scaffold(
        topBar = {
            HomeScreenAppBar(state = state, onEvent = onEvent)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent.invoke(HomeScreenEvent.OnCreateNewChat {
                    navController.navigate(NEW_CHAT_SCREEN)
                })
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            ListOfChatsSection(
                navController = navController,
                onEvent = onEvent,
                state = state
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAppBar(
    state: State<HomeScreenStates>,
    onEvent: (HomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.value.isRemovingState) {
        MikoHelperAppBar(
            title = { Text(text = "Chats") },
            actions = {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onEvent(HomeScreenEvent.OnDeleteChats)
                        onEvent(HomeScreenEvent.OnToggleRemoveState)
                    }
                )
            },
            onNavIconPressed = {
                onEvent(HomeScreenEvent.OnToggleRemoveState)
            }
        )
        return
    }
    MikoHelperAppBarNoNavigation(
        title = { Text(text = "Chats") },
        actions = {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onEvent(HomeScreenEvent.OnToggleRemoveState)
                }
            )
        }
    )
}

@Composable
fun ListOfChatsSection(
    navController: NavController,
    onEvent: (HomeScreenEvent) -> Unit,
    state: State<HomeScreenStates>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(state.value.listOfChats.size, key = {item -> item}) { item ->
            if (state.value.isRemovingState) {
                ProfileCardRemovingState(
                    chatWithMessages = state.value.listOfChats[item],
                    onSelected = { onEvent(HomeScreenEvent.OnChatSelectedForRemoval(state.value.listOfChats[item])) }
                )
                return@items
            }
            ProfileCardWithLatestMessageTimeStamp(
                chatWithMessages = state.value.listOfChats[item],
                modifier = Modifier.clickable {
                    val selectedChatItemId = state.value.listOfChats[item].chatItem.chatId
                    onEvent.invoke(HomeScreenEvent.OnChatSelected {
                        navController.navigate("$CHAT_SCREEN/$selectedChatItemId")
                    })
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    val state = remember {
        mutableStateOf(HomeScreenStates())
    }
    val navController = rememberNavController()
    MikoHelperTheme {
        HomeScreenContent(navController, {}, state)
    }
}
