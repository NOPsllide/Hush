package org.hush.feature.chat.components.stream

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hush.R
import org.hush.core.designsystem.ChatServiceBackground
import org.hush.core.designsystem.ChatServiceText
import org.hush.core.designsystem.DateBadgeShape
import org.hush.core.designsystem.DockBorder
import org.hush.core.model.Message
import org.hush.feature.chat.components.bubble.MessageBubble

@Composable
fun ChatStream(
    messages: List<Message>,
    onMessageClick: (Message) -> Unit,
    onMessageLongClick: (Message) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        reverseLayout = true,
        contentPadding = PaddingValues(top = 70.dp, bottom = 76.dp)
    ) {
        itemsIndexed(
            items = messages,
            key = { _, msg -> msg.id }
        ) { index, message ->
            val prevMessage = if (index + 1 < messages.size) messages[index + 1] else null
            val nextMessage = if (index - 1 >= 0) messages[index - 1] else null

            val isFirstInGroup = prevMessage == null || prevMessage.senderId != message.senderId
            val isLastInGroup = nextMessage == null || nextMessage.senderId != message.senderId

            val isLastMessageChronological = index == messages.size - 1

            if (isLastMessageChronological) {
                DateSeparatorBadge(
                    dateText = stringResource(R.string.chat_date_today),
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            MessageBubble(
                message = message,
                isFirstInGroup = isFirstInGroup,
                isLastInGroup = isLastInGroup,
                onClick = { onMessageClick(message) },
                onLongClick = { onMessageLongClick(message) }
            )

            val spacing = if (isLastInGroup) 7.dp else 2.5.dp
            Spacer(modifier = Modifier.height(spacing))
        }
    }
}

@Composable
fun DateSeparatorBadge(
    dateText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(DateBadgeShape)
                .background(ChatServiceBackground)
                .border(0.5.dp, DockBorder, DateBadgeShape)
                .padding(horizontal = 10.dp, vertical = 3.dp)
        ) {
            Text(
                text = dateText,
                color = ChatServiceText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
