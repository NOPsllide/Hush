package org.hush.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class HushCustomColors(
    val windowBackgroundGray: Color = WindowBackgroundGray,
    val windowBackgroundWhite: Color = WindowBackgroundWhite,
    val chatInBubble: Color = ChatInBubble,
    val chatOutBubble: Color = ChatOutBubble,
    val chatMessageTextIn: Color = ChatMessageTextIn,
    val chatMessageTextOut: Color = ChatMessageTextOut,
    val chatOutSentCheckRead: Color = ChatOutSentCheckRead,
    val chatsMessage: Color = ChatsMessage,
    val chatsName: Color = ChatsName,
    val chatsUnreadCounter: Color = ChatsUnreadCounter,
    val chatsActionBackground: Color = ChatsActionBackground,
    val chatsActionIcon: Color = ChatsActionIcon,
    val textRedRegular: Color = TextRedRegular,
    val chatServiceBackground: Color = ChatServiceBackground,
    val chatServiceText: Color = ChatServiceText,
    val inBubbleBorder: Color = InBubbleBorder,
    val dockBorder: Color = DockBorder,
    val topBarBorder: Color = TopBarBorder,
    val statusEncryptedDot: Color = StatusEncryptedDot
)

val LocalHushColors = staticCompositionLocalOf { HushCustomColors() }

private val HushColorScheme = darkColorScheme(
    primary = ChatOutBubble,
    onPrimary = ChatMessageTextOut,
    primaryContainer = DialogInputFieldActivated,
    onPrimaryContainer = WindowBackgroundWhite,
    surface = WindowBackgroundWhite,
    onSurface = ChatsName,
    surfaceVariant = ChatInBubble,
    onSurfaceVariant = ChatsMessage,
    background = WindowBackgroundGray,
    onBackground = ChatsName,
    error = TextRedRegular,
    onError = WindowBackgroundWhite,
    outline = DockBorder
)

@Composable
fun HushTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HushColorScheme,
        typography = HushTypography,
        content = content
    )
}
