package com.example.proto.di

import com.example.proto.ProtoApplication
import com.example.proto.database.PostDao
import com.example.proto.database.UserDao
import com.example.proto.repository.DefaultProtoRepository
import com.example.proto.repository.ProtoRepository
import com.example.proto.ui.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<UserDao> {
        (androidApplication() as ProtoApplication).database.userDao()
    }
    single<PostDao> {
        (androidApplication() as ProtoApplication).database.postDao()
    }

    viewModel<FeedViewModel> { (user: Long) ->
        DefaultFeedViewModel(
            selectedUser = user,
            repository = get()
        )
    }
    viewModel<PostDetailsViewModel> {
        DefaultPostDetailsViewModel()
    }
    viewModel<AddOrEditPostViewModel> {
        DefaultAddOrEditPostViewModel()
    }

    single<ProtoRepository> {
        DefaultProtoRepository(
            userDao = get(),
            postDao = get()
        )
    }
}
