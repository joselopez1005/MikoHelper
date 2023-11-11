package com.example.mikohelper.presentation.ui.chat_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mikohelper.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val repository: ChatRepository
): ViewModel() {
    var state by mutableStateOf( ChatScreenStates() )

    fun onEvent(event: ChatScreenEvent) {
        when (event) {
            is ChatScreenEvent.OnToggleKeyboard -> {
                state = state.copy(isKeyboardOpen = !state.isKeyboardOpen)
            }
        }
    }
}