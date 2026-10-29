package org.hush.feature.chat.components.dock

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hush.R
import org.hush.core.designsystem.ChatInReplyLine
import org.hush.core.designsystem.ChatMessageTextIn
import org.hush.core.designsystem.ChatMessageTextOut
import org.hush.core.designsystem.ChatOutBubble
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
    replyingMessage: Message?,
    onDismissReply: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showOmniMenu by remember { mutableStateOf(false) }
    var showStealthMenu by remember { mutableStateOf(false) }

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
                        .padding(bottom = 6.dp, start = 4.dp, end = 52.dp)
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(DockShape)
                    .background(WindowBackgroundWhite)
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 36.dp, max = 120.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = stringResource(R.string.chat_input_hint),
                                color = ChatsMessage,
                                fontSize = 14.5.sp
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

                    Spacer(modifier = Modifier.width(6.dp))

                    Box {
                        AnimatedContent(
                            targetState = text.isNotBlank(),
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "SendMicMorph"
                        ) { hasText ->
                            if (hasText) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(ChatOutBubble)
                                        .combinedClickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = onSendClick,
                                            onLongClick = { showStealthMenu = true }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Send,
                                        contentDescription = stringResource(R.string.chat_send),
                                        tint = ChatMessageTextOut,
                                        modifier = Modifier.size(17.dp)
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = onMicClick,
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Mic,
                                        contentDescription = stringResource(R.string.chat_mic),
                                        tint = ChatsMessage,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
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

            Spacer(modifier = Modifier.width(8.dp))

            Box {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(WindowBackgroundWhite)
                        .clickable { showOmniMenu = !showOmniMenu },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.chat_attach),
                        tint = ChatOutBubble,
                        modifier = Modifier.size(22.dp)
                    )
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
