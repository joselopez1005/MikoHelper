package com.example.mikohelper.domain.chat_items

import java.time.LocalDateTime

data class MessageItem(
    val messageId: Int = 0,
    val content: String,
    val role: String,
    val sentAt: LocalDateTime
) {
    companion object {
        const val USER = "user"
        const val SYSTEM = "system"
        const val ASSISTANT = "assistant"
    }
}

