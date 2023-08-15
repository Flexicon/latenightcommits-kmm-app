package dev.flexicon.latenightcommits.vm

import dev.flexicon.latenightcommits.model.Commit
import dev.flexicon.latenightcommits.vm.network.response.CommitLogResponse
import dev.flexicon.latenightcommits.vm.network.response.toModel
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
    val isLoading: Boolean = true,
    val error: String? = null,
) {
    val hasError: Boolean
        get() = !error.isNullOrBlank()
}

class CommitLogViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CommitLogUiState())
    val uiState = _uiState.asStateFlow()

    private val currentPage: Int
        get() = _uiState.value.currentPage

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        loadNextPage()
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }

    fun loadNextPage() = viewModelScope.launch {
        startLoading()
        getCommitLogPage(currentPage + 1)
            .fold(::handleLoadNextPageSuccess, ::handleLoadError)
    }

    fun refresh() = viewModelScope.launch {
        startLoading()
        getCommitLogPage(1)
            .fold(::handleRefreshSuccess, ::handleLoadError)
    }


    private suspend fun getCommitLogPage(page: Int): Result<CommitLogResponse> = runCatching {
        httpClient.get("${BASE_API_URL}/commitlog") {
            parameters {
                append("page", page.toString())
                append("per_page", "50")
            }
        }.also {
            require(it.status.isSuccess()) { "Bad Api response: ${it.bodyAsText()}" }
        }.body()
    }

    private fun startLoading() = _uiState.update {
        it.copy(isLoading = true)
    }

    private fun handleLoadNextPageSuccess(commitLog: CommitLogResponse) = _uiState.update {
        it.copy(
            commits = it.commits + commitLog.log.toModel(),
            currentPage = it.currentPage + 1,
            hasNextPage = commitLog.hasNextPage,
            isLoading = false,
            error = null,
        )
    }

    private fun handleRefreshSuccess(commitLog: CommitLogResponse) = _uiState.update {
        it.copy(
            commits = commitLog.log.toModel(),
            currentPage = 1,
            hasNextPage = commitLog.hasNextPage,
            isLoading = false,
            error = null,
        )
    }


    private fun handleLoadError(error: Throwable) = _uiState.update {
        it.copy(
            commits = emptyList(),
            currentPage = 0,
            hasNextPage = false,
            isLoading = false,
            error = error.message,
        )
    }

    companion object {
        const val BASE_API_URL = "https://latenightcommits.com/api"
    }
}
