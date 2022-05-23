package com.example.proto.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface CoroutineViewModel {
    val dispatcherProvider: DispatcherProvider

    fun CoroutineScope.launchCoroutine(block: suspend CoroutineScope.() -> Unit): Job {

        val handler = CoroutineExceptionHandler { _, exception ->
            throw exception
        }

        return launch(context = handler + dispatcherProvider.io(), block = block)
    }
}
