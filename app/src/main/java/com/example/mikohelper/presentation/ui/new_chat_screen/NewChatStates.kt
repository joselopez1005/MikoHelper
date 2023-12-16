package com.example.mikohelper.presentation.ui.new_chat_screen

import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem

data class NewChatStates (
    private val listOfCharacters: List<ChatItem> = listOf(
        ChatItem(0, "Akeshi", "Judgeful", R.drawable.ic_profile_akeshi),
        ChatItem(0, "Morgana", "Motivateful", R.drawable.ic_profile_akeshi)
    )
)