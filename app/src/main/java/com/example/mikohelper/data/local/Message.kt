package com.example.mikohelper.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "message_id")
    val messageId: Int = 0,

    @ColumnInfo(name = "chat_id")
    val chatId: Int,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "role")
    val role: String,

    @ColumnInfo(name = "sent_at")
    val sentAt: Long
)
