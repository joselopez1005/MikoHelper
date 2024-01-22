
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miko.R
import com.example.mikohelper.domain.chat_items.ChatItem
import com.example.mikohelper.presentation.ui.chat_screen.ChatScreenEvent
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme

@Composable
fun UserInputBar(
    chatItem: ChatItem,
    resetScroll: () -> Unit,
    sendButtonAction: (ChatScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var isKeyboardOpen by rememberSaveable { mutableStateOf(false) }
    if (isKeyboardOpen) {
        BackHandler {
            isKeyboardOpen = false
        }
    }

    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var textFieldFocusState by remember { mutableStateOf(false) }

    Surface(
        tonalElevation = 2.dp,
        contentColor = MaterialTheme.colorScheme.secondary,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserInputTextBar(
                onTextChanged = { textState = it },
                textFieldValue = textState,
                onTextFieldFocused = { focused ->
                    if (focused) {
                        resetScroll()
                    }
                    textFieldFocusState = focused
                },
                focusState = textFieldFocusState,
                modifier = Modifier.weight(2f)
            )
            Spacer(modifier = Modifier.width(32.dp))
            // Send button
            Button(
                modifier = Modifier.height(36.dp),
                onClick = {
                    sendButtonAction.invoke(
                        ChatScreenEvent.OnUserSendMessage(
                            message = textState.text,
                            chatItem = chatItem
                        )
                    )
                    resetScroll.invoke()
                    textState = TextFieldValue()
                }
            ) {
                Text(
                    stringResource(id = R.string.send),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun UserInputTextBar(
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier.padding(start = 16.dp)
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { onTextChanged(it) },
            modifier = Modifier
                .height(32.dp)
                .onFocusChanged { state ->
                    onTextFieldFocused(state.isFocused)
                }
                .align(Alignment.CenterStart),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Send
            ),
            maxLines = 1,
            cursorBrush = SolidColor(LocalContentColor.current),
            textStyle = MaterialTheme.typography.bodyLarge
        )
        val disableContentColor =
            MaterialTheme.colorScheme.onSurfaceVariant
        if (textFieldValue.text.isEmpty() && !focusState) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = stringResource(R.string.textfield_hint),
                style = MaterialTheme.typography.bodyLarge.copy(color = disableContentColor)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputMessage(
    chatItem: ChatItem,
    sendButtonAction: (ChatScreenEvent) -> Unit,
    resetScroll: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isKeyboardOpen by rememberSaveable { mutableStateOf(false) }
    if (isKeyboardOpen) {
        BackHandler {
            isKeyboardOpen = false
        }
    }
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var textFieldFocusState by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
    ) {
        TextField(
            value = textState,
            onValueChange = {
                textState = it
            },
            placeholder = {
                Text(
                    text = "Type a Message",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                placeholderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                cursorColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            trailingIcon = {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(70.dp)
                        .padding(end = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Face,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.clickable {
                            sendButtonAction.invoke(
                                ChatScreenEvent.OnUserSendMessage(
                                    message = textState.text,
                                    chatItem = chatItem
                                )
                            )
                            textState = TextFieldValue()
                        }
                    )
                }
            },
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (state.isFocused) {
                        resetScroll.invoke()
                    }
                    textFieldFocusState = state.isFocused
                }
        )
    }
}

@Preview
@Composable
fun UserInputMessagePreview() {
    MikoHelperTheme {
        UserInputMessage(
            chatItem = ChatItem(0, "Miko", "Nice", 0),
            sendButtonAction = {},
            resetScroll = {}
        )
    }
}

@Preview
@Composable
fun UserInputMessageDarkThemePreview() {
    MikoHelperTheme(darkTheme = true) {
        UserInputMessage(
            chatItem = ChatItem(0, "Miko", "Nice", 0),
            sendButtonAction = {},
            resetScroll = {}
        )
    }
}

@Preview
@Composable
fun UserInputBarPreview() {
    MikoHelperTheme {
        UserInputBar(
            chatItem = ChatItem(0, "Miko", "Nice", 0),
            modifier = Modifier.fillMaxWidth(),
            sendButtonAction = {},
            resetScroll = {}
        )
    }
}