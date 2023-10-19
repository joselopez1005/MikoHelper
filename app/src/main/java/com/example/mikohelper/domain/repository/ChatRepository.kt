package com.example.mikohelper.domain.repository

interface ChatRepository {
    suspend fun getChatWithMessages(chatId: Int)
}