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

    @Transaction
    @Query("SELECT * FROM chat WHERE chat_id = :chatId")
    suspend fun getChatWithMessages(chatId: Int): ChatWithMessages

}