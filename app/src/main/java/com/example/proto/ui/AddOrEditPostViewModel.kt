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

abstract class AddOrEditPostViewModel : ViewModel() {
    abstract val isLoading: Flow<Boolean>
    abstract val post: Flow<Post?>

    abstract fun fetchPost()
    abstract fun updatePost(newTitle: String, newBody: String)
}

class DefaultAddOrEditPostViewModel(
    val postId: Long,
    val userId: Long,
    private val repository: ProtoRepository,
    override val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : AddOrEditPostViewModel(), CoroutineViewModel {

    override val isLoading = actionSharedFlow<Boolean>()
    override val post = repository.selectedPost

    override fun fetchPost() {
        // it's a new post, nothing to obtain from the db
        if (postId == -1L) return

        viewModelScope.launchCoroutine {
            withLoading(isLoading) {
                repository.fetchPost(postId)
            }
        }
    }

    override fun updatePost(newTitle: String, newBody: String) {
        viewModelScope.launchCoroutine {
            withLoading(isLoading) {
                repository.addOrUpdatePost(postId, newTitle, newBody, userId)
            }
        }
    }
}
