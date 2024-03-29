package com.example.mikohelper.data.local.mappers

import com.example.mikohelper.data.local.Chat
import com.example.mikohelper.data.local.Message
import com.example.mikohelper.data.remote.completions.dto.CompletionsDto
import com.example.mikohelper.data.remote.completions.dto.MessageDto
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.MessageItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.TimeZone

fun MessageItem.toMessageEntity(chatId: Int): Message{

    return Message(
        messageId = messageId, // Auto-generated primary key
        chatId = chatId,
        content = content,
        role = role,
        sentAt = sentAt.toEpochSecond(ZoneOffset.systemDefault().rules.getOffset(sentAt))
    )
}

fun Message.toMessageDto(): MessageDto {
    return MessageDto(
        role = role,
        content = content
    )
}

fun CompletionsDto.toMessage(chatId: Int): Message {
    val firstMessage = choices.first()
    return Message(
        chatId = chatId,
        content = firstMessage.message.content,
        role = firstMessage.message.role,
        sentAt = created
    )
}

fun Message.toMessageItem(): MessageItem {
    return MessageItem(
        messageId = messageId,
        content = content,
        role = role,
        sentAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(sentAt), TimeZone.getDefault().toZoneId())
    )
}

fun Chat.toChatItem(): ChatItem {
    return ChatItem(
        chatId = chatId,
        recipientName = recipientName,
        personality = personality,
        profilePictureRef = profilePictureRef
    )
}

fun ChatItem.toChatEntity(): Chat {
    return Chat(
        chatId = chatId,
        recipientName = recipientName,
        personality = personality,
        profilePictureRef = profilePictureRef
    )
}
