package dev.flexicon.latenightcommits.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

fun formatRelativeTime(instant: Instant): String {
    val currentInstant = Clock.System.now()
    val duration: Duration = currentInstant - instant

    return when {
        duration >= 2.days -> "${duration.inWholeDays.toInt()} days ago"
        duration >= 1.days -> "1 day ago"
        duration >= 2.hours -> "${duration.inWholeHours.toInt()} hours ago"
        duration >= 1.hours -> "1 hour ago"
        duration >= 2.minutes -> "${duration.inWholeMinutes.toInt()} minutes ago"
        duration >= 1.minutes -> "1 minute ago"
        else -> "Just now"
    }
}
