package dev.flexicon.latenightcommits.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.flexicon.latenightcommits.view.CommitLog
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
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(4.dp).padding(bottom = 32.dp),
            color = MaterialTheme.colors.onBackground,
        )

        if (uiState.hasError) {
            Text(
                text = "Failed to fetch commit log: ${uiState.error}",
                color = MaterialTheme.colors.onBackground,
            )
        } else {
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
        }
    }
}
