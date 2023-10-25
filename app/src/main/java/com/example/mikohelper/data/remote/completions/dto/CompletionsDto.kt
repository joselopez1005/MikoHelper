package com.example.mikohelper.data.remote.completions.dto

import com.squareup.moshi.Json

data class CompletionsDto(
    @Json(name = "created")
    val created: Long,
    @Json(name = "choices")
    val choices: List<ChoicesDto>
)

data class ChoicesDto(
    @Json(name = "message")
    val message: MessageDto,
    @Json(name = "finish_reason")
    val finishReason: String
)

data class MessageDto(
    @Json(name = "role")
    val role: String,
    @Json(name = "content")
    val content: String
)
