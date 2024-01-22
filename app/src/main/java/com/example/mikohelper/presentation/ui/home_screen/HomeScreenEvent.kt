package com.example.mikohelper.presentation.ui.home_screen

import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.ChatItemWithMessageItems

sealed class HomeScreenEvent {
    data class OnChatSelected(val navigate: () -> Unit): HomeScreenEvent()
    data class OnCreateChat(val chatItem: ChatItem): HomeScreenEvent()
    data class OnCreateNewChat(val navigate: () -> Unit): HomeScreenEvent()
    data class OnChatSelectedForRemoval(val chatItemWithMessageItem: ChatItemWithMessageItems): HomeScreenEvent()
    object OnToggleRemoveState: HomeScreenEvent()
    object OnRefresh: HomeScreenEvent()
    object OnDeleteChats: HomeScreenEvent()
}