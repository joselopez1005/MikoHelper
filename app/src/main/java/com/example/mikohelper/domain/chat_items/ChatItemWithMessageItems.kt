package com.example.mikohelper.domain.chat_items

/**
 * Represents a unique chat with their messages
 */
data class ChatItemWithMessageItems(
    val chatItem: ChatItem,
    val messageItem: List<MessageItem>,
    val isSelected: Boolean = false  // SelectedForDeletion, scope might increase later on
)