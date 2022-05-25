package com.example.proto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proto.model.Post
import com.example.proto.repository.ProtoRepository
import com.example.proto.utils.CoroutineViewModel
import com.example.proto.utils.DefaultDispatcherProvider
import com.example.proto.utils.DispatcherProvider
import kotlinx.coroutines.flow.Flow

abstract class AddOrEditPostViewModel : ViewModel() {
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

    override val post = repository.selectedPost

    override fun fetchPost() {
        viewModelScope.launchCoroutine {
            repository.fetchOrDiscardPost(postId)
        }
    }

    override fun updatePost(newTitle: String, newBody: String) {
        viewModelScope.launchCoroutine {
            repository.addOrUpdatePost(postId, newTitle, newBody, userId)
        }
    }
}
