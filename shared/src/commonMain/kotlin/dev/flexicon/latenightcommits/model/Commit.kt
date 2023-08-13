package dev.flexicon.latenightcommits.model

data class Commit(
    val author: String,
    val avatarUrl: String,
    val createdAt: String,
    val id: String,
    val link: String,
    val message: String,
)
