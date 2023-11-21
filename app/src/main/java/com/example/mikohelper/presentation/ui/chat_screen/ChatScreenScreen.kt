package com.example.mikohelper.presentation.ui.chat_screen

import UserInputBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miko.R
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.presentation.ui.components.MikoHelperAppBar
import com.example.mikohelper.presentation.ui.components.ProfileCard
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    Scaffold(
        topBar = {
            MikoHelperAppBar(
                title = {
                    ProfileCard(
                        recipientName = "Miko",
                        recipientPicture = R.drawable.ic_profile_akeshi
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
                listOfMessages = listOf(
                    MessageItem(0, "Hi, this is a user test message", "${MessageItem.USER}", LocalDateTime.now()),
                    MessageItem(0, "Hi, this is an assistant response message", "${MessageItem.ASSISTANT}", LocalDateTime.now()),
                ),
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
            )
            UserInputBar(
                resetScroll = { },
                modifier = Modifier.navigationBarsPadding().imePadding()
            )
        }
    }
}

@Composable
fun MessagesSection(
    listOfMessages: List<MessageItem>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        for (index in listOfMessages.indices) {
            item {
                MessageBubble(
                    messageItem = listOfMessages[index],
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
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

    if (messageItem.role == MessageItem.USER) {
        backgroundBubbleColor = MaterialTheme.colorScheme.primary
        chatBubbleShape = RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
        messageAlignment = Alignment.TopEnd
    } else {
        backgroundBubbleColor = MaterialTheme.colorScheme.surfaceVariant
        chatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
        messageAlignment = Alignment.TopStart
    }

    Box(modifier = modifier
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
        ChatScreen()
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
                "USER",
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
                "USER",
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
                "ASSISTANT",
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
                "ASSISTANT",
                LocalDateTime.now()
            )
        )
    }
}