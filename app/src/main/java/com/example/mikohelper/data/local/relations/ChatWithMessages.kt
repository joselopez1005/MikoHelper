package com.example.mikohelper.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mikohelper.data.local.Chat
import com.example.mikohelper.data.local.Message

data class ChatWithMessages(
    @Embedded val chat: Chat,
    @Relation(
        parentColumn = "chat_id",
        entityColumn = "chat_id"
    )
    val messages: List<Message>
)
