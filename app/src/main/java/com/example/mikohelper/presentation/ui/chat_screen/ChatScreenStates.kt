package com.example.mikohelper.presentation.ui.chat_screen

import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.MessageItem
import java.time.LocalDateTime

data class ChatScreenStates(
    val chatItem: ChatItem = ChatItem(1, "Miko", "Helpful assistant", R.drawable.ic_profile_akeshi),
    val listOfMessages: MutableList<MessageItem> = mutableListOf(
        MessageItem(content = chatItem.personality, role = MessageItem.SYSTEM, sentAt = LocalDateTime.now())
    ),
    val isOnDeletionState: Boolean = false,
    val error: String? = null
)
