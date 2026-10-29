package org.hush.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hush.core.model.DeliveryStatus
import org.hush.core.model.Message
import org.hush.core.model.ReplySnapshot

class ChatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        loadInitialMessages()
    }

    private fun loadInitialMessages() {
        val now = System.currentTimeMillis()
        val initialList = listOf(
            Message(
                id = "msg_4",
                senderId = "self",
                content = "Got it. Self-destruct timer verified for 24h.",
                timestamp = now - 60000,
                isOutgoing = true,
                status = DeliveryStatus.READ,
                replyTo = ReplySnapshot(
                    messageId = "msg_3",
                    senderName = "Cipher Zero",
                    snippet = "Next handshake rotates automatically in 24 hours."
                )
            ),
            Message(
                id = "msg_3",
                senderId = "peer_node_1",
                content = "Next handshake rotates automatically in 24 hours.",
                timestamp = now - 120000,
                isOutgoing = false,
                status = DeliveryStatus.READ
            ),
            Message(
                id = "msg_2",
                senderId = "self",
                content = "Clean. No metadata leaks on QUIC layer either.",
                timestamp = now - 180000,
                isOutgoing = true,
                status = DeliveryStatus.READ
            ),
            Message(
                id = "msg_1",
                senderId = "peer_node_1",
                content = "Post-quantum Kyber-768 key exchange verified. All rendezvous traffic is zero-knowledge blinded.",
                timestamp = now - 240000,
                isOutgoing = false,
                status = DeliveryStatus.READ
            )
        )
        _uiState.update { it.copy(messages = initialList) }
    }

    fun onInputTextChanged(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage(isBurnOnRead: Boolean = false) {
        val currentText = _uiState.value.inputText.trim()
        if (currentText.isEmpty()) return

        val reply = _uiState.value.replyingToMessage?.let {
            ReplySnapshot(
                messageId = it.id,
                senderName = if (it.isOutgoing) "You" else _uiState.value.recipient.displayName,
                snippet = it.content
            )
        }

        val newMessage = Message(
            id = UUID.randomUUID().toString(),
            senderId = "self",
            content = currentText,
            timestamp = System.currentTimeMillis(),
            isOutgoing = true,
            status = DeliveryStatus.SENT,
            replyTo = reply,
            isBurnOnRead = isBurnOnRead
        )

        _uiState.update {
            it.copy(
                messages = listOf(newMessage) + it.messages,
                inputText = "",
                replyingToMessage = null
            )
        }
    }

    fun setReplyingTo(message: Message) {
        _uiState.update { it.copy(replyingToMessage = message) }
    }

    fun clearReply() {
        _uiState.update { it.copy(replyingToMessage = null) }
    }

    fun deleteMessage(messageId: String) {
        _uiState.update { state ->
            state.copy(messages = state.messages.filter { it.id != messageId })
        }
    }
}
