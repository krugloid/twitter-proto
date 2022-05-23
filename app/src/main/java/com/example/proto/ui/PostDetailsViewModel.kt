package com.example.proto.ui

import androidx.lifecycle.ViewModel
import com.example.proto.utils.CoroutineViewModel
import com.example.proto.utils.DefaultDispatcherProvider
import com.example.proto.utils.DispatcherProvider

abstract class PostDetailsViewModel : ViewModel() {
}

class DefaultPostDetailsViewModel(
    override val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : PostDetailsViewModel(), CoroutineViewModel {
}