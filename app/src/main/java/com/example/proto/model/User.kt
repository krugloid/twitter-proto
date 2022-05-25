package com.example.proto.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @NonNull @ColumnInfo(name = "name") val name: String,
    @NonNull @ColumnInfo(name = "display_name") val displayName: String,
    @NonNull @ColumnInfo(name = "created_at") val createdAt: Date,
    @NonNull @ColumnInfo(name = "is_current") val isCurrent: Boolean = false
)