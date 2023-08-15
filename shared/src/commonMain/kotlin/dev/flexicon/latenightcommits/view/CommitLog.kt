package dev.flexicon.latenightcommits.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.flexicon.latenightcommits.model.Commit

@Composable
fun CommitLog(commits: List<Commit>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(commits) {
            CommitLogItem(it)
        }
    }
}
