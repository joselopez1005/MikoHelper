package com.example.mikohelper.presentation.ui.home_screen

sealed class HomeScreenEvent {
    data class OnChatSelected(val navigate: () -> Unit): HomeScreenEvent()
}