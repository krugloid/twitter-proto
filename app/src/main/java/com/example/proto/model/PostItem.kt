package com.example.proto.model

data class PostItem(
    val postId: Long,
    val title: String,
    val body: String,
    val image: String?,
    val updatedAt: String,
    val userId: Long,
    val profileBadge: String,
    val color: Int
)