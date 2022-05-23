package com.example.proto.database

import androidx.room.*
import com.example.proto.model.User
import com.example.proto.model.UserPosts

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Query("UPDATE users SET is_current = 'TRUE' WHERE uid = :userId")
    fun updateCurrentUser(userId: Long)

    @Query("SELECT * FROM users WHERE is_current = 'TRUE'")
    fun getCurrentUser(): User

    @Transaction
    @Query("SELECT * FROM users")
    fun getAllPosts(): List<UserPosts>

    @Transaction
    @Query("SELECT * FROM users WHERE users.is_current = 'TRUE'")
    fun getMyPosts(): List<UserPosts>

    @Transaction
    @Query("SELECT * FROM users WHERE users.uid = :userId")
    fun getPostsByUser(userId: Long): List<UserPosts>

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>
}
