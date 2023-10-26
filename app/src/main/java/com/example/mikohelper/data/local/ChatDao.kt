package com.example.mikohelper.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mikohelper.data.local.relations.ChatWithMessages

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: Chat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Query("SELECT * FROM chat")
    suspend fun getAllChats(): List<Chat>

    @Transaction
    @Query("SELECT * FROM chat WHERE chat_id = :chatId")
    suspend fun getChatWithMessages(chatId: Int): ChatWithMessages

    @Query("SELECT * FROM message WHERE chat_id = :chatId ORDER BY sent_at DESC LIMIT 1")
    suspend fun getLatestMessageFromSpecifiedChat(chatId: Int): Message

    @Query("DELETE FROM message WHERE message_id = :messageId")
    suspend fun deleteMessage(messageId: Int)

    @Query("DELETE FROM chat WHERE chat_id=:chatId")
    suspend fun deleteChat(chatId: Int)

    @Query("DELETE FROM message WHERE chat_id=:chatId")
    suspend fun deleteMessagesFromSpecifiedChat(chatId: Int)

}