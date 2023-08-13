package dev.flexicon.latenightcommits.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Commit(
    val author: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("created_at")
    val createdAt: String,
    val id: String,
    val link: String,
    val message: String,
)
