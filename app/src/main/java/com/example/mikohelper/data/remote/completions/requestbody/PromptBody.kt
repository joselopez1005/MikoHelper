package com.example.mikohelper.data.remote.completions.requestbody

import com.google.gson.annotations.SerializedName

data class PromptBody(
    @SerializedName("model")
    val model: String,
    @SerializedName("prompt")
    val listOfMessages: List<MessageBody>,
    @SerializedName("n")
    val amountOfRequestedResponses: Int
) {
    companion object {
        const val MODEL_NAME = "gpt-3.5-turbo-0301"
    }
}
