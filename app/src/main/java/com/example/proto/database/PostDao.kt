package com.example.proto.database

import androidx.room.*
import com.example.proto.model.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPost(vararg post: Post)

    @Query("UPDATE posts SET needs_sync = NOT needs_sync")
    fun updateState()

    @Query("SELECT * FROM posts WHERE pid = :postId ORDER BY updated_at DESC")
    fun getPost(postId: Long): Post
}

