package com.example.mikohelper.presentation.ui.chat_screen

import com.example.mikohelper.domain.chat_items.ChatItem

sealed class ChatScreenEvent {
    data class OnUserSendMessage(val message: String, val chatItem: ChatItem): ChatScreenEvent()
    data class GetChatMessages(val chatItemId: Int): ChatScreenEvent()
}
