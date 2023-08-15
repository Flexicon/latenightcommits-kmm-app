package dev.flexicon.latenightcommits.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.flexicon.latenightcommits.model.Commit
import dev.flexicon.latenightcommits.vm.CommitLogViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommitLogScreen(
    viewModel: CommitLogViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = {
            println("Refreshing commit log...")
            viewModel.refresh()
        },
    )

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

        if (uiState.error.isNullOrBlank()) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .pullRefresh(pullRefreshState),
            ) {
                CommitLog(uiState.commits)
                PullRefreshIndicator(
                    refreshing = uiState.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }
        } else {
            Text(text = "Failed to fetch commit log: ${uiState.error}", color = Color.White)
        }
    }
}

@Composable
fun CommitLog(commits: List<Commit>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
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
