package com.example.proto.ui

import androidx.lifecycle.ViewModel
import com.example.proto.utils.CoroutineViewModel
import com.example.proto.utils.DefaultDispatcherProvider
import com.example.proto.utils.DispatcherProvider

abstract class AddOrEditPostViewModel : ViewModel() {
}

class DefaultAddOrEditPostViewModel(
    override val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : AddOrEditPostViewModel(), CoroutineViewModel {
}
