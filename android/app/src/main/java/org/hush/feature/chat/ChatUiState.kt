package org.hush.feature.chat

import org.hush.core.model.ChatUser
import org.hush.core.model.Message

data class ChatUiState(
    val recipient: ChatUser = ChatUser(
        id = "peer_node_1",
        displayName = "Cipher Zero",
        handle = "@cipher",
        isOnline = true,
        encryptionProtocol = "PQ-Kyber768"
    ),
    val messages: List<Message> = emptyList(),
    val inputText: String = "",
    val replyingToMessage: Message? = null,
    val isRecordingVoice: Boolean = false,
    val burnTimerSeconds: Int = 86400
)
