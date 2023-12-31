package dev.flexicon.latenightcommits.view

import BrowserLink
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.flexicon.latenightcommits.model.Commit
import dev.flexicon.latenightcommits.util.formatRelativeTime
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun CommitLogItem(commit: Commit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(bottom = 14.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            BrowserLink(
                commit.link,
                enabled = commit.link.isNotBlank(),
                modifier = Modifier.padding(bottom = 16.dp),
            ) {
                Text(
                    text = commit.message,
                    fontSize = 16.sp,
                    style = TextStyle(fontFamily = FontFamily.Monospace),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                CommitAuthorAvatar(commit)
                CommitAuthorAndTimestamp(commit)
            }
        }
    }
}

@Composable
fun CommitAuthorAvatar(commit: Commit, width: Dp = 24.dp, modifier: Modifier = Modifier) {
    val avatarModifier = Modifier.clip(RoundedCornerShape(width))
    val avatarFallback: @Composable () -> Unit = { RedactedUserAvatar(avatarModifier) }

    Box(modifier = modifier.width(width)) {
        commit.avatarUrl.takeIf { it.isNotBlank() }
            ?.let {
                KamelImage(
                    asyncPainterResource(it),
                    "${commit.author}'s avatar",
                    onLoading = { avatarFallback() },
                    onFailure = { avatarFallback() },
                    modifier = avatarModifier
                )
            } ?: avatarFallback()
    }
}

@Composable
fun CommitAuthorAndTimestamp(
    commit: Commit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.padding(horizontal = 5.dp)) {
        BrowserLink(
            "https://github.com/${commit.author}",
            enabled = commit.author.isNotBlank(),
        ) {
            Text(
                text = commit.author.ifBlank { "anonymous" },
                fontSize = 13.sp,
                modifier = Modifier.padding(end = 3.dp),
            )
        }
        Text(
            text = formatRelativeTime(commit.createdAt),
            fontSize = 13.sp,
            color = Color(0xFFA0AEC0),
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RedactedUserAvatar(modifier: Modifier = Modifier) {
    Image(
        painterResource("redacted_user.xml"),
        contentDescription = "Anonymous or redacted user avatar",
        modifier = modifier,
    )
}
