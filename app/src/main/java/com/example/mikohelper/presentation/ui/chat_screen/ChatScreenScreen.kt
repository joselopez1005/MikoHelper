package com.example.mikohelper.presentation.ui.chat_screen

import UserInputBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.presentation.ui.components.MikoHelperAppBar
import com.example.mikohelper.presentation.ui.components.ProfileCard
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun ChatScreen(
    chatItemId: Int,
    viewModel: ChatScreenViewModel = hiltViewModel()
) {
    viewModel.onEvent(ChatScreenEvent.GetChatMessages(chatItemId))
    ChatScreenContent(
        viewModel.state,
        viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChatScreenContent(
    state: State<ChatScreenStates>,
    onButtonPressed: (ChatScreenEvent) -> Unit
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MikoHelperAppBar(
                title = {
                    ProfileCard(
                        recipientName = state.value.chatItem.recipientName,
                        recipientPicture = state.value.chatItem.profilePictureRef
                    )
                },
                actions = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime)

    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            MessagesSection(
                state = state,
                scrollState = scrollState,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
            )
            UserInputBar(
                chatItem = state.value.chatItem,
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                sendButtonAction = onButtonPressed,
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MessagesSection(
    state: State<ChatScreenStates>,
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
) {
    val listOfMessages = state.value.listOfMessages.reversed()
    LazyColumn(
        state = scrollState,
        modifier = modifier,
        reverseLayout = true
    ) {
        items(listOfMessages.size) {
            MessageBubble(
                messageItem = listOfMessages[it],
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }


}

@Composable
fun MessageBubble(
    messageItem: MessageItem,
    modifier: Modifier = Modifier
) {
    val chatBubbleShape: RoundedCornerShape
    val backgroundBubbleColor: Color
    val messageAlignment: Alignment

    when (messageItem.role) {
        MessageItem.USER -> {
            backgroundBubbleColor = MaterialTheme.colorScheme.primary
            chatBubbleShape = RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
            messageAlignment = Alignment.TopEnd
        }
        MessageItem.ASSISTANT -> {
            backgroundBubbleColor = MaterialTheme.colorScheme.surfaceVariant
            chatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
            messageAlignment = Alignment.TopStart
        }
        else -> {
            return
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.align(messageAlignment)) {
            Surface(
                color = backgroundBubbleColor,
                shape = chatBubbleShape
            ) {
                ClickableMessage(message = messageItem.content, role = messageItem.role) {}
            }
        }
    }
}

@Composable
fun ClickableMessage(
    message: String,
    role: String,
    showTime: () -> Unit,
) {
    val messageText = AnnotatedString(message, paragraphStyle = ParagraphStyle())
    val textColor = if (role == MessageItem.USER) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    ClickableText(
        text = messageText,
        style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
        onClick = { showTime.invoke() },
        modifier = Modifier.padding(16.dp)
    )
}

@Preview
@Composable
fun ChatScreenPreview() {
    MikoHelperTheme {
        val state = remember {
            mutableStateOf(ChatScreenStates())
        }
        ChatScreenContent(
            state = state,
            onButtonPressed = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessageBubbleUserPreview() {
    MikoHelperTheme {
        MessageBubble(
            messageItem = MessageItem(
                0,
                "Hello, how are you?",
                "user",
                LocalDateTime.now()
            )
        )
    }
}

@Preview
@Composable
fun MessageBubbleUserPreviewDark() {
    MikoHelperTheme(darkTheme = true) {
        MessageBubble(
            messageItem = MessageItem(
                0,
                "Hello, how are you?",
                "user",
                LocalDateTime.now()
            )
        )
    }
}

@Preview
@Composable
fun MessageBubbleAssistantPreview() {
    MikoHelperTheme {
        MessageBubble(
            messageItem = MessageItem(
                0,
                "Hello, how are you?",
                "assistant",
                LocalDateTime.now()
            )
        )
    }
}

@Preview
@Composable
fun MessageBubbleUserAssistantDark() {
    MikoHelperTheme(darkTheme = true) {
        MessageBubble(
            messageItem = MessageItem(
                0,
                "Hello, how are you?",
                "assistant",
                LocalDateTime.now()
            )
        )
    }
}