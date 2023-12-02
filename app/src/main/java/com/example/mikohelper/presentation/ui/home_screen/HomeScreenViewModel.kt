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
        getChatWithMessages()
    }

    fun onEvent(event: HomeScreenEvent) {
        when(event) {
            is HomeScreenEvent.OnChatSelected -> {
                event.navigate.invoke()
            }
        }
    }

    private fun getChatWithMessages() {
        viewModelScope.launch {
            repository.getAllChats().collect { result ->
                if (result is Success) {
                    result.data?.forEach{
                        getChatWithMessages(it)
                    }
                } else {
                    _state.value = _state.value.copy(error = result.message)
                }
            }
        }
    }

    private fun getChatWithMessages(chatItem: ChatItem) {
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
}