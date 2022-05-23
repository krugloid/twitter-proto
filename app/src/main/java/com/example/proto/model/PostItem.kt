package com.example.proto.model

import java.util.Date

data class PostItem(
    val postId: Long,
    val title: String,
    val body: String,
    val image: String?,
    val updatedAt: Date,
    val userId: Long,
    val profileBadge: String,
    val color: Int
)