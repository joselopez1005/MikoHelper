package com.example.mikohelper.presentation.ui.chat_screen

import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.MessageItem

sealed class ChatScreenEvent {
    data class OnUserSendMessage(val message: String, val chatItem: ChatItem): ChatScreenEvent()
    data class GetChatMessages(val chatItemId: Int): ChatScreenEvent()
    data class OnMessageSelectedForRemoval(val messageItem: MessageItem): ChatScreenEvent()
    object OnToggleDeleteState: ChatScreenEvent()
    object OnDeleteMessages: ChatScreenEvent()
    object ResetOnDeleteState: ChatScreenEvent()
}
