package com.example.mikohelper.presentation.ui.chat_screen

import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.MessageItem

data class ChatScreenStates(
    val listOfMessages: MutableList<MessageItem> = mutableListOf(),
    val chatItem: ChatItem = ChatItem(1, "Miko", "Helpful assistant", R.drawable.ic_profile_akeshi),
    val error: String? = null
)
