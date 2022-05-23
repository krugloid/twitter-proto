package com.example.proto.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

inline fun <T> withLoading(loading: MutableStateFlow<Boolean>, block: () -> T): T {
    loading.value = true
    try {
        return block()
    } finally {
        loading.value = false
    }
}

inline fun <T> withLoading(loading: MutableSharedFlow<Boolean>, block: () -> T): T {
    loading.fireAction(true)
    try {
        return block()
    } finally {
        loading.fireAction(false)
    }
}

fun <T> actionSharedFlow() = MutableSharedFlow<T>(
    replay = 0,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

fun <T> MutableSharedFlow<T>.fireAction(data: T, scope: CoroutineScope = GlobalScope) {
    if (!tryEmit(data)) scope.launch { emit(data) }
}


