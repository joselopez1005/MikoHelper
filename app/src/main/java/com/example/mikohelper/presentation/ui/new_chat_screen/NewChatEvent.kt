package com.example.mikohelper.presentation.ui.new_chat_screen

import com.example.mikohelper.domain.chat_items.ChatItem

sealed class NewChatEvent {
    data class OnCreateChat(val selectedChat: ChatItem, val navigate: (chatId: Int) -> Unit) : NewChatEvent()
}