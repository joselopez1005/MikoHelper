package com.example.mikohelper.domain.repository

import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.ChatItemWithMessageItems
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    // Provides all chats for the purpose of being shown in the home page
    suspend fun getAllChats(): Flow<Resource<List<ChatItem>>>

    // Creates new chat when user creates a new chat with a personality
    suspend fun createNewChat(chatItem: ChatItem): Flow<Boolean>

    // Used to send message to ChatGPT and receive it's response
    suspend fun sendUserMessageAndGetResponse(messageItem: MessageItem, chatItem: ChatItem): Flow<Resource<MessageItem>>

    // Used to show information about each chat within the home page. Latest message, time, etc.
    suspend fun getChatWithMessages(chatItem: ChatItem): Flow<Resource<ChatItemWithMessageItems>>

    // Used to delete a message from a specific chat
    suspend fun deleteMessage(messageItem: MessageItem, chatItem: ChatItem): Flow<Boolean>

    // Used to delete a chat and their respective messages
    suspend fun deleteChat(chatItem: ChatItem): Flow<Boolean>

    //Used to obtain chat information
    suspend fun getChatInformation(chatId: Int): Flow<Resource<ChatItem>>
}