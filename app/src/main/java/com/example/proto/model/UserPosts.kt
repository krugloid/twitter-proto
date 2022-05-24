package com.example.proto.model

import androidx.room.Embedded
import androidx.room.Relation
import java.util.Locale

data class UserPosts(
    @Embedded val user: User,
    @Relation(
        parentColumn = "uid",
        entityColumn = "user"
    )
    val posts: List<Post>
)

fun UserPosts.toUiModel(userProfileColor: Int) = posts.map {
    PostItem(
        postId = it.pid,
        title = it.title,
        body = it.body,
        image = it.image,
        updatedAt = it.updatedAt,
        userId = user.uid,
        profileBadge = user.displayName.substring(0, 1).uppercase(Locale.getDefault()),
        color = userProfileColor
    )
}