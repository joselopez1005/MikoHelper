package com.example.mikohelper.domain.chat_items

data class ChatItem(
    val chatId: Int,
    val recipientName: String,
    val personality: String,
    val profilePictureRef: Int
)
