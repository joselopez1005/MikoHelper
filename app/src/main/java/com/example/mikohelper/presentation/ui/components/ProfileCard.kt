package com.example.mikohelper.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.domain.chat_items.ChatItemWithMessageItems
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ProfileCard(
    recipientName: String,
    recipientPicture: Int,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileIcon(recipientPicture = recipientPicture)
        Text(
            text = recipientName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun ProfileCardWithLatestMessage(
    chatWithMessages: ChatItemWithMessageItems,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            ProfileIcon(
                recipientPicture = chatWithMessages.chatItem.profilePictureRef
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = chatWithMessages.chatItem.recipientName,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = chatWithMessages.messageItem.last().content,
                    style = MaterialTheme.typography.bodySmall
                )

            }
        }
        Text(
            text = convertLocalDateTimeToLocalTime(chatWithMessages.messageItem.last().sentAt),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ProfileIcon(
    recipientPicture: Int
) {
    Image(
        painter = painterResource(id = recipientPicture),
        contentDescription = null,
        contentScale= ContentScale.Crop,
        modifier = Modifier
            .requiredSize(58.dp)
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary),
                CircleShape
            )
            .clip(CircleShape)
    )
}

fun convertLocalDateTimeToLocalTime(localDateTime: LocalDateTime): String {
    return localDateTime.format(DateTimeFormatter.ofPattern("h:mm a"))
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview(){
    MikoHelperTheme {
        ProfileCard("Jose", R.drawable.ic_profile_akeshi)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreviewDark(){
    MikoHelperTheme(darkTheme = true) {
        ProfileCard("Jose", R.drawable.ic_profile_akeshi)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardWithLatestMessagePreview() {
    MikoHelperTheme {
        ProfileCardWithLatestMessage(
            chatWithMessages = ChatItemWithMessageItems(
                chatItem = ChatItem(1, "Miko", "Helpful", R.drawable.ic_profile_akeshi),
                messageItem = listOf(MessageItem(1, "Hello", "user", LocalDateTime.now()))
            )
        )
    }
}