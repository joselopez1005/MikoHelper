package com.example.mikohelper.presentation.ui.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mikohelper.presentation.ui.components.MikoHelperAppBarNoNavigation
import com.example.mikohelper.presentation.ui.components.ProfileCardWithLatestMessage
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
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
            MikoHelperAppBarNoNavigation(
                title = { Text(text = "Chats")},
                actions = {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                    )
                }
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets

    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ListOfChatsSection(
                navController = navController,
                onEvent = onEvent,
                state = state
            )
        }
    }
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
        items(state.value.listOfChats.size) {
            ProfileCardWithLatestMessage(
                chatWithMessages = state.value.listOfChats[it],
                modifier = Modifier.clickable {
                    onEvent.invoke(HomeScreenEvent.OnChatSelected{
                        navController.navigate("chatscreen")
                    })
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    val state = remember{
        mutableStateOf(HomeScreenStates())
    }
    val navController = rememberNavController()
    MikoHelperTheme {
        HomeScreenContent(navController,{},state)
    }
}
