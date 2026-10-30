package org.hush.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val DockShape = RoundedCornerShape(22.dp)
val DateBadgeShape = RoundedCornerShape(12.dp)
val TopBarPillShape = RoundedCornerShape(20.dp)
val ReactionPillShape = RoundedCornerShape(16.dp)
val AvatarShape = RoundedCornerShape(18.dp)

fun getBubbleShape(
    isOutgoing: Boolean,
    isFirstInGroup: Boolean,
    isLastInGroup: Boolean
): RoundedCornerShape {
    val large = 18.dp
    val small = 4.dp

    return if (isOutgoing) {
        RoundedCornerShape(
            topStart = large,
            topEnd = if (isFirstInGroup) large else small,
            bottomEnd = if (isLastInGroup) large else small,
            bottomStart = large
        )
    } else {
        RoundedCornerShape(
            topStart = if (isFirstInGroup) large else small,
            topEnd = large,
            bottomEnd = large,
            bottomStart = if (isLastInGroup) large else small
        )
    }
}
