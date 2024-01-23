package com.example.mikohelper.presentation.ui.chat_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun onEvent(event: ChatScreenEvent) {
        when (event) {
            is ChatScreenEvent.OnUserSendMessage -> {
                onUserSendMessage(event.message, event.chatItem)
            }
            is ChatScreenEvent.GetChatMessages -> {
                getChatInformation(event.chatItemId).invokeOnCompletion { getChatWithMessages() }
            }
            is ChatScreenEvent.OnMessageSelectedForRemoval -> {
                val updatedMessageList = state.value.listOfMessages.map {
                    if (it.messageId == event.messageItem.messageId) {
                        it.copy(isSelectedForRemoval = !it.isSelectedForRemoval)
                    } else {
                        it
                    }
                }.toMutableList()
                _state.value = _state.value.copy(listOfMessages = updatedMessageList)
                if (_state.value.listOfMessages.none { it.isSelectedForRemoval }) {
                    _state.value = _state.value.copy(isOnDeletionState = false)
                }
            }
            is ChatScreenEvent.OnToggleDeleteState -> {
                if (_state.value.listOfMessages.none { it.isSelectedForRemoval }) {
                    _state.value = _state.value.copy(isOnDeletionState = false)
                } else {
                    _state.value = _state.value.copy(isOnDeletionState = true)
                }
            }
            is ChatScreenEvent.OnDeleteMessages -> {
               deleteMessages().invokeOnCompletion {
                   getChatWithMessages()
               }
            }
            is ChatScreenEvent.ResetOnDeleteState -> {
                val resetSelected = _state.value.listOfMessages.map {
                    if (it.isSelectedForRemoval) {
                        it.copy(isSelectedForRemoval = false)
                    } else {
                        it
                    }
                }.toMutableList()
                _state.value = _state.value.copy(listOfMessages = resetSelected)
                onEvent(ChatScreenEvent.OnToggleDeleteState)
            }

        }
    }

    private fun onUserSendMessage(message: String, chat: ChatItem) = run {
        _state.value = _state.value.copy(isLoading = true)
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
                    addMessage(result.data!!) // Also sets loading state to false
                    _state.value = _state.value.copy(isLoading = false)
                } else {
                    _state.value = _state.value.copy(error = result.message, isLoading = false)
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

    private fun deleteMessages() =
        viewModelScope.launch {
            _state.value.listOfMessages
                .filter { it.isSelectedForRemoval }
                .forEach {
                    repository.deleteMessage(it, _state.value.chatItem).collect{result ->
                        if (!result) {
                            _state.value = _state.value.copy(error = "Error: Fatal error deleting messages")
                        } else {
                            _state.value = _state.value.copy(isOnDeletionState = false)
                        }
                    }
                }
        }


}