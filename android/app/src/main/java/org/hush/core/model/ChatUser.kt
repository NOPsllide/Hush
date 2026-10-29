package org.hush.core.model

data class ChatUser(
    val id: String,
    val displayName: String,
    val handle: String,
    val avatarUrl: String? = null,
    val isOnline: Boolean = true,
    val encryptionProtocol: String = "PQ-Kyber768"
)
