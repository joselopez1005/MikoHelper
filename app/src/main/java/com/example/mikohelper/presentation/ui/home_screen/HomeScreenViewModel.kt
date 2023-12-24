package com.example.mikohelper.presentation.ui.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.repository.ChatRepository
import com.example.mikohelper.domain.util.Resource.Error
import com.example.mikohelper.domain.util.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: ChatRepository
) :ViewModel() {

    private val _state = mutableStateOf(HomeScreenStates())
    val state: State<HomeScreenStates> = _state

    init {
        getAllChatsWithMessages()
    }

    fun onEvent(event: HomeScreenEvent) {
        when(event) {
            is HomeScreenEvent.OnChatSelected -> {
                event.navigate.invoke()
            }
            is HomeScreenEvent.OnCreateChat -> {
                createNewChat(event.chatItem).invokeOnCompletion { getAllChatsWithMessages() }
            }
            is HomeScreenEvent.OnCreateNewChat -> {
                event.navigate.invoke()
            }
        }
    }

    private fun getAllChatsWithMessages() {
        // Need to reset list to account for obtaining all chats again with their messages
        _state.value = _state.value.copy(listOfChats = mutableListOf())

        viewModelScope.launch {
            repository.getAllChats().collect { result ->
                if (result is Success) {
                    result.data?.forEach{
                        getAllChatsWithMessages(it)
                    }
                } else {
                    _state.value = _state.value.copy(error = result.message)
                }
            }
        }
    }

    private fun getAllChatsWithMessages(chatItem: ChatItem) = run {
        viewModelScope.launch {
            repository.getChatWithMessages(chatItem).collect { result ->
                if (result is Success) {
                    val updatedState = _state.value.copy(
                        listOfChats = (_state.value.listOfChats + result.data!!).toMutableList()
                    )
                    _state.value = updatedState
                }
                if (result is Error) {
                    _state.value = _state.value.copy(error = result.message)
                }
            }
        }
    }

    private fun createNewChat(chatItem: ChatItem) = run {
        viewModelScope.launch {
            repository.createNewChat(chatItem).collect { result ->
                if (result is Success) {
                    _state.value = _state.value.copy(createdChat = result.data!!)
                }
                if (result is Error) {
                    _state.value = _state.value.copy(error = result.message)
                }
            }
        }
    }
}