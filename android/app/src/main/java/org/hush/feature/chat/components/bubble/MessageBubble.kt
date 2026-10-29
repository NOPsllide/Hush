package org.hush.feature.chat.components.bubble

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import org.hush.R
import org.hush.core.designsystem.ChatInBubble
import org.hush.core.designsystem.ChatInReplyLine
import org.hush.core.designsystem.ChatMessageTextIn
import org.hush.core.designsystem.ChatMessageTextOut
import org.hush.core.designsystem.ChatOutBubble
import org.hush.core.designsystem.ChatOutReplyLine
import org.hush.core.designsystem.ChatOutSentCheckRead
import org.hush.core.designsystem.ChatsMessage
import org.hush.core.designsystem.InBubbleBorder
import org.hush.core.designsystem.TextRedRegular
import org.hush.core.designsystem.WindowBackgroundWhite
import org.hush.core.designsystem.getBubbleShape
import org.hush.core.model.DeliveryStatus
import org.hush.core.model.Message

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun MessageBubble(
    message: Message,
    isFirstInGroup: Boolean,
    isLastInGroup: Boolean,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOutgoing = message.isOutgoing
    val bubbleShape = getBubbleShape(
        isOutgoing = isOutgoing,
        isFirstInGroup = isFirstInGroup,
        isLastInGroup = isLastInGroup
    )

    val bubbleBg = if (isOutgoing) ChatOutBubble else ChatInBubble
    val textColor = if (isOutgoing) ChatMessageTextOut else ChatMessageTextIn
    val timeColor = if (isOutgoing) ChatMessageTextOut.copy(alpha = 0.70f) else ChatsMessage
    val tickColor = ChatOutSentCheckRead

    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val formattedTime = timeFormatter.format(Date(message.timestamp))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        contentAlignment = if (isOutgoing) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.78f)
                .then(
                    if (isOutgoing) {
                        Modifier.wrapContentWidth(Alignment.End)
                    } else {
                        Modifier.wrapContentWidth(Alignment.Start)
                    }
                )
                .clip(bubbleShape)
                .background(bubbleBg)
                .then(
                    if (!isOutgoing) {
                        Modifier.border(0.75.dp, InBubbleBorder, bubbleShape)
                    } else {
                        Modifier
                    }
                )
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
                .padding(horizontal = 13.dp, vertical = 9.dp)
        ) {
            Column {
                if (message.replyTo != null) {
                    val replyLineColor = if (isOutgoing) ChatOutReplyLine else ChatInReplyLine
                    val replyBgColor = if (isOutgoing) WindowBackgroundWhite.copy(alpha = 0.15f) else WindowBackgroundWhite

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(replyBgColor)
                            .padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(26.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(replyLineColor)
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                        Column {
                            Text(
                                text = message.replyTo.senderName,
                                color = if (isOutgoing) ChatMessageTextOut else ChatInReplyLine,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1
                            )
                            Text(
                                text = message.replyTo.snippet,
                                color = if (isOutgoing) ChatMessageTextOut.copy(alpha = 0.8f) else ChatsMessage,
                                fontSize = 11.sp,
                                maxLines = 1
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = message.content,
                        color = textColor,
                        fontSize = 15.5.sp,
                        lineHeight = 21.sp,
                        fontWeight = if (isOutgoing) FontWeight.Medium else FontWeight.Normal,
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .padding(end = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(top = 4.dp)
                    ) {
                        if (message.isBurnOnRead) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = stringResource(R.string.chat_action_burn_after_reading),
                                tint = if (isOutgoing) ChatMessageTextOut else TextRedRegular,
                                modifier = Modifier
                                    .size(11.dp)
                                    .padding(end = 2.dp)
                            )
                        }

                        Text(
                            text = formattedTime,
                            color = timeColor,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Normal
                        )

                        if (isOutgoing) {
                            Spacer(modifier = Modifier.width(3.dp))
                            when (message.status) {
                                DeliveryStatus.SENT -> {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = tickColor,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                                DeliveryStatus.DELIVERED, DeliveryStatus.READ -> {
                                    Icon(
                                        imageVector = Icons.Default.DoneAll,
                                        contentDescription = null,
                                        tint = tickColor,
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                                DeliveryStatus.SENDING -> {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(tickColor.copy(alpha = 0.5f))
                                    )
                                }
                                DeliveryStatus.ERROR -> {
                                    Text(
                                        text = "!",
                                        color = TextRedRegular,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun Modifier.wrapContentWidth(align: Alignment.Horizontal): Modifier = this
