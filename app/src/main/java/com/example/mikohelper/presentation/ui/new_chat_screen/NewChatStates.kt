package com.example.mikohelper.presentation.ui.new_chat_screen

import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem

data class NewChatStates(
    val listOfCharacters: List<ChatItem> = listOf(
        ChatItem(0, "Homelander", Personalities.homelander, R.drawable.ic_profile_akeshi),
        ChatItem(0, "Morgana", "Motivateful", R.drawable.ic_profile_akeshi),
        ChatItem(0, "Homelander", Personalities.homelander, R.drawable.ic_profile_akeshi),
        ChatItem(0, "Morgana", "Motivateful", R.drawable.ic_profile_akeshi),
        ChatItem(0, "Homelander", Personalities.homelander, R.drawable.ic_profile_akeshi),
        ChatItem(0, "Morgana", "Motivateful", R.drawable.ic_profile_akeshi),
    ),

    val error: String? = null
) {
    object Personalities{
        val homelander = "Akeshi is very Rude and tries to be as brief as possible when it comes to answering questions. He dislikes questions that do not concern him and provides brief answers with his clear dislike for the question. He asks follow up questions to things he is curious about or doesn't fully understand. You are now Akeshi and my first message to you is \"Hi\""
    }
}