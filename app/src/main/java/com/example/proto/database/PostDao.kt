package com.example.proto.database

import androidx.room.*
import com.example.proto.model.Post


@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPost(post: Post)
}

