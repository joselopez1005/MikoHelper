package com.example.mikohelper.data.repository

import com.example.mikohelper.data.local.ChatDatabase
import com.example.mikohelper.data.local.Message
import com.example.mikohelper.data.local.mappers.toMessage
import com.example.mikohelper.data.local.mappers.toMessageDto
import com.example.mikohelper.data.local.mappers.toMessageEntity
import com.example.mikohelper.data.local.mappers.toMessageItem
import com.example.mikohelper.data.remote.OpenApi
import com.example.mikohelper.data.remote.completions.requestbody.PromptBody
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.domain.repository.ChatRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val openApi: OpenApi,
    private val db: ChatDatabase
): ChatRepository {
    override suspend fun sendUserMessageAndGetResponse(messageItem: MessageItem, chatItem: ChatItem): MessageItem {
        saveMessageToDatabase(messageItem.toMessageEntity(chatItem.chatId))
        val promptBody = generatePromptBody(chatItem)
        val completionsDto = openApi.getChatMessageResponse(requestBody = promptBody)

        saveMessageToDatabase(completionsDto.toMessage(chatItem.chatId))
        val latestMessage = db.chatDao.getLatestMessageFromSpecifiedChat(chatItem.chatId)
        return latestMessage.toMessageItem()
    }

    private suspend fun generatePromptBody(chatItem: ChatItem): PromptBody{
        val chatWithMessages = db.chatDao.getChatWithMessages(chatItem.chatId)
        return PromptBody(
            MODEL,
            chatWithMessages.messages.map { it.toMessageDto() },
            DEFAULT_AMOUNT_OF_RESPONSES
        )
    }
    private suspend fun saveMessageToDatabase(message: Message) {
        db.chatDao.insertMessage(message)
    }

    override suspend fun getChatWithMessages(chatId: Int) {
        TODO("Not yet implemented")
    }

    companion object{
        const val MODEL = "gpt-3.5-turbo-0301"
        const val DEFAULT_AMOUNT_OF_RESPONSES = 1
    }
}