package com.example.mikohelper.data.repository

import com.example.mikohelper.data.local.ChatDatabase
import com.example.mikohelper.data.remote.OpenApi
import com.example.mikohelper.domain.repository.ChatRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val openApi: OpenApi,
    private val db: ChatDatabase
): ChatRepository {
    override suspend fun getChatWithMessages(chatId: Int) {
        TODO("Not yet implemented")
    }
}