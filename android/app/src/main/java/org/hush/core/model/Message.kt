package org.hush.core.model

enum class DeliveryStatus {
    SENDING,
    SENT,
    DELIVERED,
    READ,
    ERROR
}

data class ReplySnapshot(
    val messageId: String,
    val senderName: String,
    val snippet: String
)

data class Message(
    val id: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val isOutgoing: Boolean,
    val status: DeliveryStatus = DeliveryStatus.SENT,
    val replyTo: ReplySnapshot? = null,
    val isBurnOnRead: Boolean = false
)
