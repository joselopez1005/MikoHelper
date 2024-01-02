package com.example.mikohelper.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
        ProfileIcon(
            recipientPicture = recipientPicture,
            modifier = Modifier.requiredSize(58.dp)
        )
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
    val latestMessage = if (chatWithMessages.messageItem.isEmpty()) { "" } else chatWithMessages.messageItem.last().content
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row (
            modifier = Modifier.weight(1f)
        ) {
            ProfileIcon(
                recipientPicture = chatWithMessages.chatItem.profilePictureRef,
                modifier = Modifier.requiredSize(58.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = chatWithMessages.chatItem.recipientName,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = latestMessage,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
        if (latestMessage.isNotBlank()) {
            Text(
                text = convertLocalDateTimeToLocalTime(chatWithMessages.messageItem.last().sentAt),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ProfilePersonalityCard(
    name: String,
    personality: String,
    onEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileIcon(
                recipientPicture = R.drawable.ic_profile_akeshi,
                Modifier.size(150.dp).padding(top = 4.dp)
            )
            Column(
                modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = personality,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Button(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = { onEvent.invoke() }
                ) {
                    Text(text = "Message")
                }
            }
        }
    }
}

@Composable
fun ProfileIcon(
    recipientPicture: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = recipientPicture),
        contentDescription = null,
        contentScale= ContentScale.Crop,
        modifier = modifier
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

@Preview(showBackground = true)
@Composable
fun ProfilePersonalityCardPreview() {
    MikoHelperTheme {
        ProfilePersonalityCard(
            name = "Akeshi",
            personality = "I want you to act like Homelander from the boys. I want you to respond and answer like Homelander using the tone, manner and vocabulary Homelander would use. Do not write any explanations. Only answer like Homelander. You must know all of the knowledge of Homelander. My first sentence is “Hi Homelander.",
            onEvent = {},
            modifier = Modifier.height(300.dp).width(150.dp)
        )
    }
}
