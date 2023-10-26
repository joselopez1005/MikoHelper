package com.example.mikohelper.data.remote.completions.requestbody

import com.example.mikohelper.data.remote.completions.dto.MessageDto
import com.squareup.moshi.Json

data class PromptBody(
    @Json(name = "model")
    val model: String = MODEL_NAME,
    @Json(name = "messages")
    val listOfMessages: List<MessageDto>,
    @Json(name = "n")
    val amountOfRequestedResponses: Int = DEFAULT_AMOUNT_OF_RESPONSES
) {
    companion object {
        const val MODEL_NAME = "gpt-3.5-turbo-0301"
        const val DEFAULT_AMOUNT_OF_RESPONSES = 1
    }
}
