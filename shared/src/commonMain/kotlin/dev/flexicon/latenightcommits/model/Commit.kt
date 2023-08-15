package dev.flexicon.latenightcommits.model

import kotlinx.datetime.Instant

data class Commit(
    val author: String,
    val avatarUrl: String,
    val createdAt: Instant,
    val id: String,
    val link: String,
    val message: String,
)
