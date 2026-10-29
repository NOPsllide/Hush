package org.hush.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.hush.core.designsystem.WindowBackgroundGray
import org.hush.core.model.Message
import org.hush.feature.chat.components.dock.FloatingDock
import org.hush.feature.chat.components.stream.ChatStream
import org.hush.feature.chat.components.topbar.StealthTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedMessageForAction by remember { mutableStateOf<Message?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(WindowBackgroundGray)
    ) {
        ChatStream(
            messages = uiState.messages,
            onMessageClick = {},
            onMessageLongClick = { msg -> selectedMessageForAction = msg },
            modifier = Modifier.fillMaxSize()
        )

        StealthTopBar(
            user = uiState.recipient,
            onBackClick = onBackClick,
            onCallClick = {},
            modifier = Modifier.align(Alignment.TopCenter)
        )

        FloatingDock(
            text = uiState.inputText,
            onTextChange = viewModel::onInputTextChanged,
            onSendClick = { viewModel.sendMessage(isBurnOnRead = false) },
            onSendSilentClick = { viewModel.sendMessage(isBurnOnRead = false) },
            onSendBurnClick = { viewModel.sendMessage(isBurnOnRead = true) },
            onSendScheduleClick = {},
            onMicClick = {},
            replyingMessage = uiState.replyingToMessage,
            onDismissReply = viewModel::clearReply,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
