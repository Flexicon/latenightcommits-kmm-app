package dev.flexicon.latenightcommits.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.flexicon.latenightcommits.model.Commit

val dummyCommits = listOf(
    Commit(
        author = "JohnWick",
        avatarUrl = "https://64.media.tumblr.com/avatar_09eb98b6eba4_128.pnj",
        createdAt = "2023/08/12 18:23 UTC",
        id = "3241bde086b35c75ae870008d262fce3bf29c897",
        link = "https://github.com/Flexicon/latenightcommits-kmm-app/commit/3241bde086b35c75ae870008d262fce3bf29c897",
        message = "You want a war or do you just want to give me a gun?",
    ),
    Commit(
        author = "roo_barb_ftp",
        avatarUrl = "https://avatars.githubusercontent.com/u/140785446?v=4",
        createdAt = "2023/08/12 17:01 UTC",
        id = "3241bde086b35c75ae870008d262fce3bf29c897",
        link = "https://github.com/Flexicon/latenightcommits-kmm-app/commit/3241bde086b35c75ae870008d262fce3bf29c897",
        message = "some random commit message here",
    ),
)


@Composable
fun CommitLogScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "🍺 Late Night Commits",
            fontSize = 30.sp,
            color = MaterialTheme.colors.onBackground,
        )
        Text(
            text = "When the pressures of being 10x just overwhelm you",
            fontSize = 13.sp,
            modifier = Modifier.padding(4.dp).padding(bottom = 20.dp),
            color = MaterialTheme.colors.onBackground,
        )
        CommitLog(dummyCommits)
    }
}

@Composable
fun CommitLog(commits: List<Commit>) {
    LazyColumn {
        items(commits) {
            CommitLogItem(it)
        }
    }
}

@Composable
fun CommitLogItem(commit: Commit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(bottom = 12.dp)) {
        Column {
            Text(
                text = commit.message,
                modifier = Modifier.fillMaxWidth()
                    .padding(5.dp, 8.dp),
            )
            Text(
                text = "- " + commit.author,
                fontSize = 13.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .padding(bottom = 5.dp),
            )
        }
    }
}
