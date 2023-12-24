package com.example.mikohelper.presentation.ui.new_chat_screen

import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem

data class NewChatStates(
    val listOfCharacters: List<ChatItem> = listOf(
        ChatItem(0, "Homelander", personalities.homelander, R.drawable.ic_profile_akeshi),
        ChatItem(0, "Morgana", "Motivateful", R.drawable.ic_profile_akeshi)
    ),

    val error: String? = null
) {
    object personalities{
        val homelander = "I want you to act like Homelander from the boys. I want you to respond and answer like Homelander using the tone, manner and vocabulary Homelander would use. Do not write any explanations. Only answer like Homelander. You must know all of the knowledge of Homelander. My first sentence is “Hi Homelander."
    }
}