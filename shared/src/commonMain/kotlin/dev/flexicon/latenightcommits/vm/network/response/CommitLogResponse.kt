package dev.flexicon.latenightcommits.vm.network.response

import dev.flexicon.latenightcommits.model.Commit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommitLogResponse(
    val log: List<CommitLogResponseItem>,
    @SerialName("has_next_page")
    val hasNextPage: Boolean,
)

@Serializable
data class CommitLogResponseItem(
    val author: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("created_at")
    val createdAt: String,
    val id: String,
    val link: String,
    val message: String,
)

fun List<CommitLogResponseItem>.toModel(): List<Commit> =
    map { it.toModel() }

fun CommitLogResponseItem.toModel(): Commit =
    Commit(
        author = author,
        avatarUrl = avatarUrl,
        createdAt = createdAt,
        id = id,
        link = link,
        message = message,
    )
