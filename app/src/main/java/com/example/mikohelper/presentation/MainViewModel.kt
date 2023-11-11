package com.example.mikohelper.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.domain.repository.ChatRepository
import com.example.mikohelper.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    init {
        viewModelScope.launch {
            val messageItem = MessageItem(1, "Hi", "user", LocalDateTime.now())
            val chatItem = ChatItem(1, "Esoj", "Mad", 1234)
//            chatRepository.createNewChat(chatItem).collect{
//                if(it) {
//                  Log.d("MainViewModel", "Chat was added")
//                } else {
//                    Log.d("MainViewModel", "Error adding chat")
//                }
//            }
            chatRepository.sendUserMessageAndGetResponse(messageItem, chatItem).collect{
                if (it is Resource.Success) {
                    Log.d("MainViewModel", "Response: ${it.data}")
                } else {
                    Log.d("MainViewModel", "Error obtaining respons")
                }
            }
            chatRepository.deleteChat(chatItem).collect{
                if (it) {

                }
            }

        }

    }

}