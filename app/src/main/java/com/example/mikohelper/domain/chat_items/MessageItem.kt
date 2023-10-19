package com.example.mikohelper.domain.chat_items

import java.time.LocalDateTime

data class MessageItem(
    val content: String,
    val role: String,
    val sentAt: LocalDateTime
)
