@file:OptIn(ExperimentalFoundationApi::class)

package com.example.mikohelper.presentation.ui.chat_screen

import UserInputMessage
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mikohelper.domain.chat_items.MessageItem
import com.example.mikohelper.domain.util.NavigationUtil
import com.example.mikohelper.presentation.ui.chat_screen.ChatScreenEvent.GetChatMessages
import com.example.mikohelper.presentation.ui.chat_screen.ChatScreenEvent.OnToggleDeleteState
import com.example.mikohelper.presentation.ui.components.MikoHelperAppBar
import com.example.mikohelper.presentation.ui.components.ProfileCard
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun ChatScreen(
    chatItemId: Int,
    navController: NavController,
    viewModel: ChatScreenViewModel = hiltViewModel()
) {
    viewModel.onEvent(GetChatMessages(chatItemId))
    ChatScreenContent(
        viewModel.state,
        navController = navController,
        viewModel::onEvent
    )
    BackHandler {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(NavigationUtil.Results.REFRESH_NEEDED, true)

        navController.popBackStack()
    }
    if(!viewModel.state.value.error.isNullOrBlank()) {
        val error = viewModel.state.value.error!!
        AlertDialog(
            icon = {
                   Icon(
                       imageVector = Icons.Filled.Info,
                       contentDescription = null,
                       tint = MaterialTheme.colorScheme.onSurfaceVariant
                   )
            },
            title = {
                    Text(
                        text = "Something Went Wrong!",
                        style = MaterialTheme.typography.titleLarge
                    )
            },
            text = {
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            onDismissRequest = { viewModel.onEvent(ChatScreenEvent.DismissErrorDialog) },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.onEvent(ChatScreenEvent.DismissErrorDialog) },
                ) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChatScreenContent(
    state: State<ChatScreenStates>,
    navController: NavController,
    onEvent: (ChatScreenEvent) -> Unit
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            if (state.value.isOnDeletionState) {
                MikoHelperDeleteAppBar(state = state, onEvent = onEvent)
            } else {
                MikoHelperSearchAppBar(state = state, navController = navController)
            }
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
                onEvent = onEvent,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
            )
            UserInputMessage(
                chatItem = state.value.chatItem,
                sendButtonAction = onEvent,
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
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
    onEvent: (ChatScreenEvent) -> Unit,
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
            if (it == 0) {
                MessageBubble(
                    messageItem = listOfMessages[it],
                    onEvent = onEvent,
                    state = state,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                return@items
            }
            MessageBubble(
                messageItem = listOfMessages[it],
                onEvent = onEvent,
                state = state,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
    if (state.value.isLoading) {
        MessageBubbleLoading(
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
        )
    }


}

@Composable
fun MessageBubble(
    messageItem: MessageItem,
    onEvent: (ChatScreenEvent) -> Unit,
    state: State<ChatScreenStates>,
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
            .combinedClickable(
                onClick = {
                    if (state.value.isOnDeletionState) {
                        onEvent(ChatScreenEvent.OnMessageSelectedForRemoval(messageItem))
                    }
                },
                onLongClick = {
                    onEvent(ChatScreenEvent.OnMessageSelectedForRemoval(messageItem))
                    onEvent(OnToggleDeleteState)
                }
            )
    ) {
        Column(modifier = Modifier.align(messageAlignment)) {
            Surface(
                color = if (messageItem.isSelectedForRemoval) MaterialTheme.colorScheme.inversePrimary else backgroundBubbleColor,
                shape = chatBubbleShape
            ) {
                BubbleMessage(message = messageItem.content, role = messageItem.role) {}
            }
        }
    }
}

@Composable
fun BubbleMessage(
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
    Text(
        text = messageText,
        style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun MessageBubbleLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp),
            ) {
            LoadingAnimation3(
                circleSize = 12.dp,
                circleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MikoHelperSearchAppBar(
    state: State<ChatScreenStates>,
    navController: NavController
) {
    MikoHelperAppBar(
        title = {
            ProfileCard(
                recipientName = state.value.chatItem.recipientName,
                recipientPicture = state.value.chatItem.profilePictureRef
            )
        },
        actions = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
        onNavIconPressed = {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(NavigationUtil.Results.REFRESH_NEEDED, true)

            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MikoHelperDeleteAppBar(
    state: State<ChatScreenStates>,
    onEvent: (ChatScreenEvent) -> Unit
) {
    MikoHelperAppBar(
        title = {
            ProfileCard(
                recipientName = state.value.chatItem.recipientName,
                recipientPicture = state.value.chatItem.profilePictureRef
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onEvent.invoke(ChatScreenEvent.OnDeleteMessages)
                }
            )
        },
        onNavIconPressed = {
            onEvent.invoke(ChatScreenEvent.ResetOnDeleteState)
        }
    )
}

@Composable
fun LoadingAnimation3(
    circleColor: Color = Color(0xFF35898F),
    circleSize: Dp = 36.dp,
    animationDelay: Int = 400,
    initialAlpha: Float = 0.3f,
    modifier: Modifier = Modifier
) {

    // 3 circles
    val circles = listOf(
        remember {
            Animatable(initialValue = initialAlpha)
        },
        remember {
            Animatable(initialValue = initialAlpha)
        },
        remember {
            Animatable(initialValue = initialAlpha)
        }
    )

    circles.forEachIndexed { index, animatable ->

        LaunchedEffect(Unit) {

            // Use coroutine delay to sync animations
            delay(timeMillis = (animationDelay / circles.size).toLong() * index)

            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    // container for circles
    Row(
        modifier = modifier
        //.border(width = 2.dp, color = Color.Magenta)
    ) {

        // adding each circle
        circles.forEachIndexed { index, animatable ->

            // gap between the circles
            if (index != 0) {
                Spacer(modifier = Modifier.width(width = 6.dp))
            }

            Box(
                modifier = Modifier
                    .size(size = circleSize)
                    .clip(shape = CircleShape)
                    .background(
                        color = circleColor
                            .copy(alpha = animatable.value)
                    )
            ) {
            }
        }
    }
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
            onEvent = {},
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessageBubbleUserPreview() {
    val state = remember {
        mutableStateOf(ChatScreenStates())
    }

    MikoHelperTheme {
        MessageBubble(
            messageItem = MessageItem(
                0,
                "Hello, how are you?",
                "user",
                LocalDateTime.now()
            ),
            onEvent = {},
            state = state
        )
    }
}

@Preview
@Composable
fun MessageBubbleUserPreviewDark() {
    val state = remember {
        mutableStateOf(ChatScreenStates())
    }
    MikoHelperTheme(darkTheme = true) {
        MessageBubble(
            messageItem = MessageItem(
                0,
                "Hello, how are you?",
                "user",
                LocalDateTime.now()
            ),
            onEvent = {},
            state = state
        )
    }
}

@Preview
@Composable
fun MessageBubbleAssistantPreview() {
    val state = remember {
        mutableStateOf(ChatScreenStates())
    }
    MikoHelperTheme {
        MessageBubble(
            messageItem = MessageItem(
                0,
                "Hello, how are you?",
                "assistant",
                LocalDateTime.now()
            ),
            onEvent = {},
            state = state
        )
    }
}

@Preview
@Composable
fun MessageBubbleUserAssistantDark() {
    val state = remember {
        mutableStateOf(ChatScreenStates())
    }
    MikoHelperTheme(darkTheme = true) {
        MessageBubble(
            messageItem = MessageItem(
                0,
                "Hello, how are you?",
                "assistant",
                LocalDateTime.now()
            ),
            onEvent = {},
            state = state
        )
    }
}