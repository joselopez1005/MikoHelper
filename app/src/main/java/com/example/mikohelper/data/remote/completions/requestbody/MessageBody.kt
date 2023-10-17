package com.example.mikohelper.data.remote.completions.requestbody

import com.google.gson.annotations.SerializedName

data class MessageBody(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)
