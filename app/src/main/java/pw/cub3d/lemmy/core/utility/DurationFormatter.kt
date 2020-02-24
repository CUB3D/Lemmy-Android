package pw.cub3d.lemmy.core.utility

import org.threeten.bp.Duration
import kotlin.math.floor

fun Duration.toRelativeString(): String {
    val negative = isNegative

    val dura = abs()

    val weeks = floor(dura.toDays() / 7.0)
    val days = floor(dura.toDays() - weeks * 7.0)
    val hours = dura.toHours()
    val minutes = floor(dura.toMinutes() - hours * 60.0)

    val timePart = when {
        dura.toDays() >= 14 -> "$weeks weeks"
        dura.toDays() >= 7 -> "$weeks week and $days days"
        dura.toHours() >= 48 -> "${dura.toHours() / 24} days"
        dura.toHours() >= 24 -> "${dura.toHours() / 24} day"
        hours > 1 && minutes > 0 -> "$hours hours and $minutes minutes"
        hours == 1L && minutes > 1 -> "1 hour and $minutes minutes"
        hours == 1L && minutes == 1.0 -> "1 hour and 1 minute"
        hours == 1L && minutes == 0.0 -> "1 hour"
        else -> "${dura.toMinutes()} minutes"
    }

    return if(negative) {
        "$timePart ago"
    } else {
        "In $timePart"
    }
}