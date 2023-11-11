package com.example.mikohelper.presentation.ui.chat_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme
import java.time.LocalDateTime

@Composable
fun MessageBubble(
    messageItem: MessageItem
) {
    val chatBubbleShape: RoundedCornerShape
    val backgroundBubbleColor: Color

    if (messageItem.role == MessageItem.USER) {
        backgroundBubbleColor = MaterialTheme.colorScheme.primary
        chatBubbleShape = RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
    } else {
        backgroundBubbleColor = MaterialTheme.colorScheme.surfaceVariant
        chatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = chatBubbleShape
        ) {
            ClickableMessage(message = messageItem.content, role = messageItem.role) {}
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