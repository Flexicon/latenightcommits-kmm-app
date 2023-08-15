package dev.flexicon.latenightcommits.view

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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
    val imageModifier = Modifier.clip(RoundedCornerShape(24.dp))

    Card(
        modifier = modifier.padding(bottom = 14.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = commit.message,
                fontSize = 16.sp,
                style = TextStyle(fontFamily = FontFamily.Monospace),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            )
            Row {
                Box(modifier = Modifier.width(24.dp)) {
                    if (commit.avatarUrl.isNotBlank()) {
                        KamelImage(
                            asyncPainterResource(commit.avatarUrl),
                            "${commit.author}'s avatar",
                            onLoading = { RedactedUserAvatar() },
                            onFailure = { RedactedUserAvatar() },
                            modifier = imageModifier
                        )
                    } else {
                        RedactedUserAvatar(modifier = imageModifier)
                    }
                }
                Text(
                    text = "${commit.author.ifBlank { "anonymous" }} " +
                            "- ${formatRelativeTime(commit.createdAt)}",
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 5.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RedactedUserAvatar(modifier: Modifier = Modifier) {
    Image(
        painterResource("redacted_user.xml"),
        contentDescription = "anonymous",
        modifier = modifier,
    )
}
