package com.example.proto.model

import android.graphics.Color
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

fun UserPosts.toUiModel() = posts.map {
    PostItem(
        postId = it.pid,
        title = it.title,
        body = it.body,
        image = it.image,
        updatedAt = it.updatedAt,
        userId = user.uid,
        profileBadge = user.displayName.substring(0, 1).uppercase(Locale.getDefault()),
        // TODO: create aka ProfileColorProvider to delegate calculations of the color basing on a number of user's posts
        color = Color.CYAN
    )
}