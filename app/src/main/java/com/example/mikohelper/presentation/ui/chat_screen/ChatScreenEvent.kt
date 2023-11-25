package com.example.mikohelper.presentation.ui.chat_screen

import com.example.mikohelper.domain.chat_items.ChatItem

sealed class ChatScreenEvent {
    class OnUserSendMessage(val message: String, val chatItem: ChatItem): ChatScreenEvent()
}
