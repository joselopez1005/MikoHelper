package com.example.mikohelper.presentation.ui.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.presentation.ui.components.MikoHelperAppBarNoNavigation
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent() {
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
        }
    }
}

@Composable
fun ListOfChatsSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        
    }
}

@Composable
fun ChatListItem(
    chatItem: ChatItem,
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
fun ChatItemPreview() {
    MikoHelperTheme {
        ChatListItem(chatItem = ChatItem(1, "Miko", "Humble", R.drawable.ic_profile_akeshi))
    }
}
@Preview
@Composable
fun HomeScreenContentPreview() {
    MikoHelperTheme {
        HomeScreenContent()
    }
}
