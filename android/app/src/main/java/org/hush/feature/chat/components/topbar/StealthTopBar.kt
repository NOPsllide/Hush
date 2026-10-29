package org.hush.feature.chat.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hush.R
import org.hush.core.designsystem.AvatarShape
import org.hush.core.designsystem.ChatOutBubble
import org.hush.core.designsystem.ChatsMessage
import org.hush.core.designsystem.ChatsName
import org.hush.core.designsystem.StatusEncryptedDot
import org.hush.core.designsystem.TopBarBorder
import org.hush.core.designsystem.TopBarPillShape
import org.hush.core.designsystem.WindowBackgroundGray
import org.hush.core.designsystem.WindowBackgroundWhite
import org.hush.core.model.ChatUser

@Composable
fun StealthTopBar(
    user: ChatUser,
    onBackClick: () -> Unit,
    onBurnTimerClick: () -> Unit,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(WindowBackgroundGray.copy(alpha = 0.92f))
            .border(width = 0.5.dp, color = TopBarBorder)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .clip(TopBarPillShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    )
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.chat_back),
                        tint = ChatsName,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Box(
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(AvatarShape)
                            .background(WindowBackgroundWhite),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.displayName.take(1).uppercase(),
                            color = ChatOutBubble,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    if (user.isOnline) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .clip(CircleShape)
                                .background(StatusEncryptedDot)
                                .border(1.5.dp, WindowBackgroundGray, CircleShape)
                                .align(Alignment.BottomEnd)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = user.displayName,
                        color = ChatsName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(R.string.chat_status_encrypted),
                        color = ChatsMessage,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(WindowBackgroundWhite)
                        .border(0.5.dp, TopBarBorder, CircleShape)
                        .clickable(onClick = onBurnTimerClick)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = stringResource(R.string.chat_burn_timer_title),
                            tint = ChatOutBubble,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = stringResource(R.string.chat_burn_timer_24h),
                            color = ChatOutBubble,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = onCallClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = stringResource(R.string.chat_p2p_call),
                        tint = ChatsMessage,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
