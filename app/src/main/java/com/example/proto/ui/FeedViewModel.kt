package com.example.proto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proto.extensions.actionSharedFlow
import com.example.proto.extensions.withLoading
import com.example.proto.model.PostItem
import com.example.proto.model.toUiModel
import com.example.proto.repository.ProtoRepository
import com.example.proto.utils.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class FeedViewModel : ViewModel() {
    abstract val isLoading: Flow<Boolean>
    abstract val postItems: Flow<List<PostItem>>
    abstract val isCurrentUser: Flow<Boolean>

    abstract fun refresh()
    abstract fun invalidateSyncState()
}

class DefaultFeedViewModel(
    val selectedUser: Long,
    private val repository: ProtoRepository,
    private val userActivityEvaluator: UserActivityEvaluator,
    override val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : FeedViewModel(), CoroutineViewModel {

    override val isLoading = actionSharedFlow<Boolean>()
    override val isCurrentUser = repository.currentUser.map {
        it?.uid == selectedUser
    }

    override val postItems = repository.allPosts.map { posts ->
        posts.flatMap { userPosts ->
            val userProfileColor = userActivityEvaluator.numberOfPostsToRange(userPosts.posts.size).color
            userPosts.toUiModel(userProfileColor)
        }
    }

    override fun refresh() {
        viewModelScope.launchCoroutine {
            withLoading(isLoading) {
                val postSync = async {
                    if (selectedUser != -1L) {
                        repository.fetchPostsByUser(selectedUser)
                    } else {
                        repository.fetchPosts()
                    }
                }
                val currentUserSync = async { repository.fetchCurrentUser() }

                awaitAll(postSync, currentUserSync)
            }
        }
    }

    override fun invalidateSyncState() {
        viewModelScope.launchCoroutine {
            withLoading(isLoading) {
                repository.updateSyncState()
            }
        }
    }
}