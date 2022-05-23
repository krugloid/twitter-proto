package com.example.proto.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
//    fun main(): CoroutineDispatcher
//    fun default(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}

class DefaultDispatcherProvider : DispatcherProvider {
//    override fun main(): CoroutineDispatcher = Dispatchers.Main
//    override fun default(): CoroutineDispatcher = Dispatchers.Default
    override fun io(): CoroutineDispatcher = Dispatchers.IO
}
