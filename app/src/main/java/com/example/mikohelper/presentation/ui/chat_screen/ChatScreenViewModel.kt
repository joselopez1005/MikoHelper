package com.example.mikohelper.presentation.ui.chat_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.domain.repository.ChatRepository
import com.example.mikohelper.domain.util.Resource.Error
import com.example.mikohelper.domain.util.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val repository: ChatRepository
): ViewModel() {
    private val _state = mutableStateOf(ChatScreenStates())
    val state: State<ChatScreenStates> = _state

    init {
        getChatWithMessages()
    }

    fun onEvent(event: ChatScreenEvent) {
        when (event) {
            is ChatScreenEvent.OnUserSendMessage -> {
                onUserSendMessage(event.message, event.chatItem)
            }
            is ChatScreenEvent.GetChatMessages -> {
                getChatInformation(event.chatItemId).invokeOnCompletion { getChatWithMessages() }
            }

        }
    }

    private fun onUserSendMessage(message: String, chat: ChatItem) = run {
        val messageItem = MessageItem(
            content = message,
            role = MessageItem.USER,
            sentAt = LocalDateTime.now()
        )
        addMessage(messageItem)
        viewModelScope.launch {
            repository.sendUserMessageAndGetResponse(
                messageItem = messageItem,
                chatItem = chat
            ).collect { result ->
                if (result is Success) {
                    addMessage(result.data!!)
                } else {
                    _state.value = _state.value.copy(error = result.message)
                }
            }
        }
    }

    private fun addMessage(message: MessageItem) {
        val updatedState = _state.value.copy(
            listOfMessages = (_state.value.listOfMessages + message).toMutableList()
        )
        _state.value = updatedState
    }
    private fun getChatWithMessages() = run {
        viewModelScope.launch {
            repository.getChatWithMessages(_state.value.chatItem).collect { result ->
                _state.value = if (result is Success) {
                    _state.value.copy(listOfMessages = result.data!!.messageItem.toMutableList())
                } else {
                    _state.value.copy(error = result.message)
                }
            }
        }
    }

    private fun getChatInformation(chatId: Int) = run {
        viewModelScope.launch {
            repository.getChatInformation(chatId).collect { result ->
                if (result is Success) {
                    _state.value = _state.value.copy(chatItem = result.data!!)
                }
                if (result is Error) {
                    _state.value = _state.value.copy(error = result.message)
                }
            }
        }
    }

    //TODO: Remove this when implementing home screen
    private fun createChat() = run {
        viewModelScope.launch {
            repository.createNewChat(ChatItem(0, "Miko", "Helpful assistant", R.drawable.ic_profile_akeshi)).collect {
                Log.d("Creating chat: ", it.toString())
            }
        }
    }
}