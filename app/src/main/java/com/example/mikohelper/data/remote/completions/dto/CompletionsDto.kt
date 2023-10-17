package com.example.mikohelper.data.remote.completions.dto

import com.google.gson.annotations.SerializedName

data class CompletionsDto(
    @SerializedName("created")
    val created: Long,
    @SerializedName("choices")
    val choices: List<ChoicesDto>
)

data class ChoicesDto(
    @SerializedName("message")
    val message: List<MessageDto>,
    @SerializedName("finish_reason")
    val finishReason: String
)

data class MessageDto(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)
