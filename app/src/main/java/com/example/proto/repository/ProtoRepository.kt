package com.example.proto.repository

import com.example.proto.database.PostDao
import com.example.proto.database.UserDao
import com.example.proto.extensions.withLoading
import com.example.proto.model.User
import com.example.proto.model.UserPosts
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ProtoRepository {
    val isLoading: Flow<Boolean>

    val allPosts: Flow<List<UserPosts>>
    suspend fun fetchPosts()
    suspend fun fetchPostsByUser(userId: Long)

    val currentUser: Flow<User?>
    suspend fun fetchCurrentUser()

    suspend fun updateSyncState()
}

@ExperimentalCoroutinesApi
class DefaultProtoRepository(
    private val userDao: UserDao,
    private val postDao: PostDao
) : ProtoRepository {

    override val isLoading = MutableStateFlow(false)

    override val allPosts = MutableStateFlow<List<UserPosts>>(emptyList())
    override val currentUser = MutableStateFlow<User?>(null)

    override suspend fun fetchPosts() = withLoading(isLoading) {
        allPosts.value = userDao.getAllPosts()
    }

    override suspend fun fetchPostsByUser(userId: Long) = withLoading(isLoading) {
        allPosts.value = userDao.getPostsByUser(userId)
    }

    override suspend fun fetchCurrentUser() = withLoading(isLoading) {
        currentUser.value = userDao.getCurrentUser()
    }

    override suspend fun updateSyncState() = withLoading(isLoading) {
        postDao.updateState()
        allPosts.value = userDao.getMyPosts()
    }
}