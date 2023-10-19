package com.example.mikohelper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mikohelper.data.local.Chat
import com.example.mikohelper.data.local.Message
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chat = listOf (
            Chat(recipientName =  "Miko", personality = "A helpful assistant", profilePictureRef =  1234),
            Chat(recipientName =  "Niko", personality = "A evil assistant", profilePictureRef =  1234),
        )

        val messages = listOf(
            Message( chatId = 0, content = "Hey how are you", role =  "User", sentAt = 1),
            Message( chatId = 0, content = "Hey how are you", role =  "User", sentAt = 1),
            Message( chatId = 0, content = "Hey how are you", role =  "User", sentAt = 1),
        )



        setContent {
            MikoHelperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MikoHelperTheme {
        Greeting("Android")
    }
}