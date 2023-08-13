package dev.flexicon.latenightcommits.vm

import dev.flexicon.latenightcommits.model.Commit
import dev.flexicon.latenightcommits.vm.network.response.CommitLogResponse
import dev.flexicon.latenightcommits.vm.network.response.CommitLogResponseItem
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CommitLogUiState(
    val commits: List<Commit> = emptyList(),
    val currentPage: Int = 0,
    val hasNextPage: Boolean = true,
    val error: String? = null,
)

class CommitLogViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CommitLogUiState())
    val uiState = _uiState.asStateFlow()

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        loadCommitLog()
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }

    fun loadCommitLog() {
        viewModelScope.launch {
            getCommitLog().fold(
                { commitLog ->
                    _uiState.update {
                        it.copy(
                            commits = it.commits + commitLog.log.toModel(),
                            currentPage = it.currentPage + 1,
                            hasNextPage = commitLog.hasNextPage,
                            error = null,
                        )
                    }
                },
                { error ->
                    _uiState.update {
                        it.copy(
                            commits = emptyList(),
                            currentPage = 0,
                            hasNextPage = false,
                            error = error.message,
                        )
                    }
                },
            )
        }
    }

    private suspend fun getCommitLog(): Result<CommitLogResponse> = runCatching {
        httpClient.get("${BASE_API_URL}/commitlog") {
            parameters {
                append("page", (_uiState.value.currentPage + 1).toString())
                append("per_page", "50")
            }
        }.also {
            require(it.status.isSuccess()) { "Bad Api response: ${it.bodyAsText()}" }
        }.body()
    }

    private fun List<CommitLogResponseItem>.toModel(): List<Commit> =
        map { it.toModel() }

    private fun CommitLogResponseItem.toModel(): Commit =
        Commit(
            author = author,
            avatarUrl = avatarUrl,
            createdAt = createdAt,
            id = id,
            link = link,
            message = message,
        )

    companion object {
        const val BASE_API_URL = "https://latenightcommits.com/api"
    }
}
