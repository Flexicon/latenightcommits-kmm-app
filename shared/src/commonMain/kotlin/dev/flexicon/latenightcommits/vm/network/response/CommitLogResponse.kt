package dev.flexicon.latenightcommits.vm.network.response

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
