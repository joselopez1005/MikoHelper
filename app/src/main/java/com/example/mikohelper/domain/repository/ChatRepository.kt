package com.example.mikohelper.domain.repository

import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.MessageItem

interface ChatRepository {

    suspend fun sendUserMessageAndGetResponse(messageItem: MessageItem, chatItem: ChatItem): MessageItem
    suspend fun getChatWithMessages(chatId: Int)
}