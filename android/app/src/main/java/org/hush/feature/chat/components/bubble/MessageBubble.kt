package org.hush.feature.chat.components.bubble

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import org.hush.core.designsystem.ChatInBubble
import org.hush.core.designsystem.ChatInReplyLine
import org.hush.core.designsystem.ChatMessageTextIn
import org.hush.core.designsystem.ChatMessageTextOut
import org.hush.core.designsystem.ChatOutBubble
import org.hush.core.designsystem.ChatOutReplyLine
import org.hush.core.designsystem.ChatOutSentCheckRead
import org.hush.core.designsystem.ChatsMessage
import org.hush.core.designsystem.TextRedRegular
import org.hush.core.designsystem.WindowBackgroundWhite
import org.hush.core.designsystem.getBubbleShape
import org.hush.core.model.DeliveryStatus
import org.hush.core.model.Message

@OptIn(ExperimentalFoundationApi::class)
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
    val timeColor = if (isOutgoing) ChatMessageTextOut.copy(alpha = 0.65f) else ChatsMessage
    val tickColor = ChatOutSentCheckRead

    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val formattedTime = timeFormatter.format(Date(message.timestamp))

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        contentAlignment = if (isOutgoing) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        val maxBubbleWidth = maxWidth * 0.78f

        Box(
            modifier = Modifier
                .clip(bubbleShape)
                .background(bubbleBg)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
                .padding(horizontal = 11.dp, vertical = 6.dp)
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
                    Spacer(modifier = Modifier.height(4.dp))
                }

                TelegramStyleTextAndTime(
                    content = message.content,
                    formattedTime = formattedTime,
                    isOutgoing = isOutgoing,
                    status = message.status,
                    textColor = textColor,
                    timeColor = timeColor,
                    tickColor = tickColor,
                    maxBubbleWidth = maxBubbleWidth
                )
            }
        }
    }
}

@Composable
private fun TelegramStyleTextAndTime(
    content: String,
    formattedTime: String,
    isOutgoing: Boolean,
    status: DeliveryStatus,
    textColor: Color,
    timeColor: Color,
    tickColor: Color,
    maxBubbleWidth: Dp,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    val textStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = if (isOutgoing) FontWeight.Medium else FontWeight.Normal,
        fontSize = 15.5.sp,
        lineHeight = 21.sp,
        color = textColor
    )

    val maxBubbleWidthPx = with(density) { maxBubbleWidth.roundToPx() }

    val textLayoutResult = remember(content, maxBubbleWidthPx) {
        textMeasurer.measure(
            text = AnnotatedString(content),
            style = textStyle,
            constraints = Constraints(maxWidth = maxBubbleWidthPx)
        )
    }

    Layout(
        content = {
            Text(
                text = content,
                style = textStyle
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedTime,
                    color = timeColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal
                )
                if (isOutgoing) {
                    Spacer(modifier = Modifier.width(3.dp))
                    when (status) {
                        DeliveryStatus.SENT -> {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = tickColor,
                                modifier = Modifier.size(15.5.dp)
                            )
                        }
                        DeliveryStatus.DELIVERED, DeliveryStatus.READ -> {
                            Icon(
                                imageVector = Icons.Default.DoneAll,
                                contentDescription = null,
                                tint = tickColor,
                                modifier = Modifier.size(16.5.dp)
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
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        val textPlaceable = measurables[0].measure(constraints.copy(minWidth = 0, maxWidth = maxBubbleWidthPx))
        val timePlaceable = measurables[1].measure(Constraints())

        val timeWidth = timePlaceable.width
        val timeHeight = timePlaceable.height
        val textWidth = textPlaceable.width
        val textHeight = textPlaceable.height

        val lineCount = textLayoutResult.lineCount
        val lastLineWidth = if (lineCount > 0) {
            textLayoutResult.getLineRight(lineCount - 1) - textLayoutResult.getLineLeft(lineCount - 1)
        } else {
            textWidth.toFloat()
        }
        val spacing = 8.dp.roundToPx()

        val canFitOnLastLine: Boolean
        val totalWidth: Int
        val totalHeight: Int

        if (lineCount <= 1) {
            val singleLineWidth = (lastLineWidth + spacing + timeWidth).toInt()
            if (singleLineWidth <= maxBubbleWidthPx) {
                canFitOnLastLine = true
                totalWidth = singleLineWidth
                totalHeight = maxOf(textHeight, timeHeight)
            } else {
                canFitOnLastLine = false
                totalWidth = textWidth
                totalHeight = textHeight + timeHeight
            }
        } else {
            val remainingSpace = textWidth - lastLineWidth
            if (remainingSpace >= timeWidth + spacing) {
                canFitOnLastLine = true
                totalWidth = textWidth
                totalHeight = textHeight
            } else {
                canFitOnLastLine = false
                val extendedWidth = (lastLineWidth + spacing + timeWidth).toInt().coerceAtMost(maxBubbleWidthPx)
                totalWidth = maxOf(textWidth, extendedWidth)
                totalHeight = textHeight + timeHeight
            }
        }

        layout(totalWidth, totalHeight) {
            textPlaceable.placeRelative(0, 0)
            val timeY = totalHeight - timeHeight
            timePlaceable.placeRelative(
                x = totalWidth - timeWidth,
                y = timeY
            )
        }
    }
}
