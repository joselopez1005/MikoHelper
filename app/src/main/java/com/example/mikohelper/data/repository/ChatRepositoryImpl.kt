package com.example.mikohelper.data.repository

import com.example.mikohelper.data.local.ChatDatabase
import com.example.mikohelper.data.local.mappers.toChatEntity
import com.example.mikohelper.data.local.mappers.toChatItem
import com.example.mikohelper.data.local.mappers.toMessage
import com.example.mikohelper.data.local.mappers.toMessageDto
import com.example.mikohelper.data.local.mappers.toMessageEntity
import com.example.mikohelper.data.local.mappers.toMessageItem
import com.example.mikohelper.data.remote.OpenApi
import com.example.mikohelper.data.remote.completions.requestbody.PromptBody
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.ChatItemWithMessageItems
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.domain.repository.ChatRepository
import com.example.mikohelper.domain.util.Resource
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val openApi: OpenApi,
    private val db: ChatDatabase
) : ChatRepository {

    // TODO: Handle IO errors
    override suspend fun getAllChats() = flow {
        try {
            val listOfChats = db.chatDao.getAllChats()
            emit(Resource.Success(listOfChats.map { it.toChatItem() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error: Can't Retrieve List Of Chats"))
        }
    }

    override suspend fun createNewChat(chatItem: ChatItem) = flow {
        try {
            db.chatDao.insertChat(chatItem.toChatEntity())
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }

    override suspend fun sendUserMessageAndGetResponse(
        messageItem: MessageItem,
        chatItem: ChatItem
    ) = flow {
        try {
            db.chatDao.insertMessage(messageItem.toMessageEntity(chatItem.chatId))
            val promptBody = generatePromptBody(chatItem)
            val completionsDto = openApi.getChatMessageResponse(requestBody = promptBody)

            db.chatDao.insertMessage(completionsDto.toMessage(chatItem.chatId))
            val latestMessage = db.chatDao.getLatestMessageFromSpecifiedChat(chatItem.chatId)
            emit(Resource.Success(latestMessage.toMessageItem()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Unknown Error: Sending Message"))
        }
    }

    override suspend fun getChatWithMessages(chatItem: ChatItem) = flow {
        try {
            val chatWithMessages = db.chatDao.getChatWithMessages(chatItem.chatId)
            val chatItemWithMessageItems = ChatItemWithMessageItems(
                chatItem = chatWithMessages.chat.toChatItem(),
                messageItem = chatWithMessages.messages.map { it.toMessageItem() }
            )
            emit(Resource.Success(chatItemWithMessageItems))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: "Unknown Error: Obtaining Chat With Messages"))
        }
    }

    private suspend fun generatePromptBody(chatItem: ChatItem): PromptBody {
        val chatWithMessages = db.chatDao.getChatWithMessages(chatItem.chatId)
        return PromptBody(
            listOfMessages = chatWithMessages.messages.map { it.toMessageDto() },
        )
    }
}
