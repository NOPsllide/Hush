package org.hush.feature.chat.components.dock

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hush.R
import org.hush.core.designsystem.ChatInReplyLine
import org.hush.core.designsystem.ChatMessageTextIn
import org.hush.core.designsystem.ChatOutBubble
import org.hush.core.designsystem.ChatOutSentCheckRead
import org.hush.core.designsystem.ChatsMessage
import org.hush.core.designsystem.DockShape
import org.hush.core.designsystem.WindowBackgroundWhite
import org.hush.core.model.Message

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FloatingDock(
    text: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onSendSilentClick: () -> Unit,
    onSendBurnClick: () -> Unit,
    onSendScheduleClick: () -> Unit,
    onMicClick: () -> Unit,
    onEmojiClick: () -> Unit,
    replyingMessage: Message?,
    onDismissReply: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showOmniMenu by remember { mutableStateOf(false) }
    var showStealthMenu by remember { mutableStateOf(false) }

    val hasText = text.isNotBlank()

    val dockBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF252428),
                WindowBackgroundWhite
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .imePadding()
            .navigationBarsPadding()
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        AnimatedVisibility(
            visible = replyingMessage != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            if (replyingMessage != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp, start = 4.dp, end = 58.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(WindowBackgroundWhite)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(24.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(ChatInReplyLine)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(R.string.chat_action_reply),
                            color = ChatInReplyLine,
                            fontSize = 11.5.sp
                        )
                        Text(
                            text = replyingMessage.content,
                            color = ChatsMessage,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                    }
                    IconButton(
                        onClick = onDismissReply,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = ChatsMessage,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 50.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = DockShape,
                        ambientColor = Color.Black.copy(alpha = 0.5f),
                        spotColor = Color.Black.copy(alpha = 0.5f)
                    )
                    .clip(DockShape)
                    .background(dockBrush)
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onEmojiClick,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SentimentSatisfiedAlt,
                            contentDescription = stringResource(R.string.chat_emoji),
                            tint = ChatsMessage,
                            modifier = Modifier.size(23.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(2.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 38.dp, max = 120.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = stringResource(R.string.chat_input_hint),
                                color = ChatsMessage,
                                fontSize = 15.sp
                            )
                        }
                        BasicTextField(
                            value = text,
                            onValueChange = onTextChange,
                            textStyle = TextStyle(
                                color = ChatMessageTextIn,
                                fontSize = 15.sp,
                                lineHeight = 20.sp
                            ),
                            cursorBrush = SolidColor(ChatOutBubble),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }

                    AnimatedVisibility(
                        visible = !hasText,
                        enter = fadeIn(animationSpec = spring()) + slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = spring()
                        ),
                        exit = fadeOut(animationSpec = spring()) + slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = spring()
                        )
                    ) {
                        IconButton(
                            onClick = onMicClick,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = stringResource(R.string.chat_mic),
                                tint = ChatsMessage,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = Color.Black.copy(alpha = 0.4f),
                            spotColor = Color.Black.copy(alpha = 0.4f)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(ChatOutBubble)
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                if (hasText) {
                                    onSendClick()
                                } else {
                                    showOmniMenu = !showOmniMenu
                                }
                            },
                            onLongClick = {
                                if (hasText) {
                                    showStealthMenu = true
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = hasText,
                        transitionSpec = {
                            (scaleIn(animationSpec = spring()) + fadeIn(animationSpec = spring())) togetherWith
                                    (scaleOut(animationSpec = spring()) + fadeOut(animationSpec = spring()))
                        },
                        label = "ActionIconMorph"
                    ) { isTyping ->
                        if (isTyping) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = stringResource(R.string.chat_send),
                                tint = ChatOutSentCheckRead,
                                modifier = Modifier.size(21.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.chat_attach),
                                tint = ChatOutSentCheckRead,
                                modifier = Modifier.size(23.dp)
                            )
                        }
                    }
                }

                DropdownMenu(
                    expanded = showOmniMenu,
                    onDismissRequest = { showOmniMenu = false },
                    modifier = Modifier.background(WindowBackgroundWhite)
                ) {
                    OmniMenuItem(
                        icon = Icons.Default.Image,
                        text = stringResource(R.string.chat_omni_gallery),
                        onClick = { showOmniMenu = false }
                    )
                    OmniMenuItem(
                        icon = Icons.Default.Description,
                        text = stringResource(R.string.chat_omni_file),
                        onClick = { showOmniMenu = false }
                    )
                    OmniMenuItem(
                        icon = Icons.Default.LocalFireDepartment,
                        text = stringResource(R.string.chat_omni_secret_note),
                        onClick = { showOmniMenu = false }
                    )
                    OmniMenuItem(
                        icon = Icons.Default.LocationOn,
                        text = stringResource(R.string.chat_omni_location),
                        onClick = { showOmniMenu = false }
                    )
                }

                DropdownMenu(
                    expanded = showStealthMenu,
                    onDismissRequest = { showStealthMenu = false },
                    modifier = Modifier.background(WindowBackgroundWhite)
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.chat_action_send_silently), color = ChatMessageTextIn, fontSize = 13.5.sp) },
                        onClick = {
                            showStealthMenu = false
                            onSendSilentClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.chat_action_burn_after_reading), color = ChatOutBubble, fontSize = 13.5.sp) },
                        onClick = {
                            showStealthMenu = false
                            onSendBurnClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.chat_action_schedule), color = ChatMessageTextIn, fontSize = 13.5.sp) },
                        onClick = {
                            showStealthMenu = false
                            onSendScheduleClick()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun OmniMenuItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ChatOutBubble,
                modifier = Modifier.size(18.dp)
            )
        },
        text = {
            Text(
                text = text,
                color = ChatMessageTextIn,
                fontSize = 13.5.sp
            )
        },
        onClick = onClick
    )
}
