package com.example.mikohelper.presentation.ui.new_chat_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mikohelper.domain.repository.ChatRepository
import com.example.mikohelper.domain.util.Resource
import com.example.mikohelper.presentation.ui.new_chat_screen.NewChatEvent.OnCreateChat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewChatScreenViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val _state = mutableStateOf(NewChatStates())
    val state: State<NewChatStates> = _state

    fun onEvent(event: NewChatEvent) {
        when (event) {
            is OnCreateChat -> {
                viewModelScope.launch {
                    repository.createNewChat(event.selectedChat).collect{ result ->
                        if (result is Resource.Success) {
                            event.navigate.invoke(result.data!!.chatId)
                        }
                        if (result is Resource.Error) {
                            _state.value = _state.value.copy(error = result.message)
                        }
                    }
                }
            }
        }
    }
}