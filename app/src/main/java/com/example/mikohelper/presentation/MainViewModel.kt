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
            Log.d("MainViewModel", "Init function")
            val messageItem = MessageItem("Hi", "user", LocalDateTime.now())
            var listOfChats: List<ChatItem>
            chatRepository.getAllChats().collect{ result ->
                listOfChats = result.data?: listOf()
                Log.d("MainViewModel", "ListOfChats = ${result.data}")

                chatRepository.sendUserMessageAndGetResponse(messageItem, listOfChats.first()).collect{ message ->
                    if ( message is Resource.Success) {
                        Log.d("MainViewModel", message.message ?: "error")
                    }
                }

                chatRepository.getChatWithMessages(listOfChats.first()).collect {
                    if (it is Resource.Success) {
                        Log.d("MainViewModel", "ChatWithMessages: ${it.data}")
                    }
                }
            }


        }

    }

}