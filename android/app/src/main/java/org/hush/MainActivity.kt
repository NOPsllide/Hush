package org.hush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import org.hush.core.designsystem.HushTheme
import org.hush.feature.chat.ChatScreen
import org.hush.feature.chat.ChatViewModel

class MainActivity : ComponentActivity() {

    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HushTheme {
                ChatScreen(
                    viewModel = chatViewModel,
                    onBackClick = { finish() }
                )
            }
        }
    }
}
