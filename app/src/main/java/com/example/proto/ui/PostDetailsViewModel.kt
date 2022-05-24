package com.example.proto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proto.extensions.actionSharedFlow
import com.example.proto.extensions.withLoading
import com.example.proto.model.Post
import com.example.proto.repository.ProtoRepository
import com.example.proto.utils.CoroutineViewModel
import com.example.proto.utils.DefaultDispatcherProvider
import com.example.proto.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

abstract class PostDetailsViewModel : ViewModel() {
    abstract val isLoading: Flow<Boolean>
    abstract val isCurrentUser: Flow<Boolean>
    abstract val post: Flow<Post?>

    abstract fun fetchPost()
}

class DefaultPostDetailsViewModel(
    val postId: Long,
    private val repository: ProtoRepository,
    override val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : PostDetailsViewModel(), CoroutineViewModel {

    override val isLoading = actionSharedFlow<Boolean>()
    override val post = repository.selectedPost
    override val isCurrentUser = combine(post, repository.currentUser) { post, user ->
        if (user != null && post != null) {
            return@combine user.uid == post.user
        }
        false
    }

    init {
        viewModelScope.launchCoroutine {
            withLoading(isLoading) {
                repository.fetchCurrentUser()
            }
        }
    }

    override fun fetchPost() {
        viewModelScope.launchCoroutine {
            withLoading(isLoading) {
                repository.fetchPost(postId)
            }
        }
    }
}