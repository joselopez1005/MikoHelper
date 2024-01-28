package com.example.mikohelper.data.remote

import com.example.miko.BuildConfig
import com.example.mikohelper.data.remote.completions.dto.CompletionsDto
import com.example.mikohelper.data.remote.completions.requestbody.PromptBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenApi {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getChatMessageResponse(
        @Header("Authorization") auth: String = "Bearer ${BuildConfig.OPEN_API_KEY}",
        @Body requestBody: PromptBody
    ): CompletionsDto
}