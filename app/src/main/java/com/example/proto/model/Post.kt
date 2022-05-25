package com.example.proto.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "posts",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("uid"),
            childColumns = arrayOf("user")
        )
    )
)
data class Post(
    @PrimaryKey(autoGenerate = true) val pid: Long = 0L,
    @NonNull @ColumnInfo(name = "title") val title: String,
    @NonNull @ColumnInfo(name = "body") val body: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
    @ColumnInfo(name = "image") val image: String,
    @NonNull @ColumnInfo(name = "needs_sync") val needsSync: Boolean = false,
    @NonNull @ColumnInfo(name = "updated_at") val updatedAt: Date = Date(),
    @NonNull @ColumnInfo(name = "user") val user: Long
)