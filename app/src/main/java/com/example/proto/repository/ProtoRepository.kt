package com.example.proto.repository

import com.example.proto.database.PostDao
import com.example.proto.database.UserDao
import com.example.proto.extensions.withLoading
import com.example.proto.model.Post
import com.example.proto.model.User
import com.example.proto.model.UserPosts
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ProtoRepository {
    val isLoading: Flow<Boolean>

    val allPosts: Flow<List<UserPosts>>
    val selectedPost: Flow<Post?>
    suspend fun fetchPost(postId: Long)
    suspend fun fetchPosts()
    suspend fun fetchPostsByUser(userId: Long)
    suspend fun addOrUpdatePost(postId: Long, title: String, body: String, userId: Long)

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
    override val selectedPost = MutableStateFlow<Post?>(null)
    override val currentUser = MutableStateFlow<User?>(null)

    override suspend fun fetchPosts() = withLoading(isLoading) {
        allPosts.value = userDao.getAllPosts()
    }

    override suspend fun fetchPostsByUser(userId: Long) = withLoading(isLoading) {
        allPosts.value = userDao.getPostsByUser(userId)
    }

    override suspend fun fetchPost(postId: Long) = withLoading(isLoading) {
        selectedPost.value = postDao.getPost(postId)
    }

    override suspend fun addOrUpdatePost(postId: Long, title: String, body: String, userId: Long) =
        withLoading(isLoading) {
            var newPost = Post(
                title = title,
                body = body,
                user = userId
            )
            if (postId != -1L) {
                newPost = newPost.copy(pid = postId)
            }
            postDao.addPost(newPost)
        }

    override suspend fun fetchCurrentUser() = withLoading(isLoading) {
        currentUser.value = userDao.getCurrentUser()
    }

    override suspend fun updateSyncState() = withLoading(isLoading) {
        postDao.updateState()
        allPosts.value = userDao.getMyPosts()
    }
}