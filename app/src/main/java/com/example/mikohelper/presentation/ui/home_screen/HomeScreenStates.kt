package com.example.mikohelper.presentation.ui.home_screen

import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.ChatItemWithMessageItems

data class HomeScreenStates (
    val listOfChats: MutableList<ChatItemWithMessageItems> = mutableListOf(),
    val isRemovingState: Boolean = false, // User removing chats from chat list
    val error: String? = null,
    val createdChat: ChatItem? = null
)