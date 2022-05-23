package com.example.proto.di

import com.example.proto.ui.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<FeedViewModel> { (user: Long) ->
        DefaultFeedViewModel(
            selectedUser = user
        )
    }
    viewModel<PostDetailsViewModel> {
        DefaultPostDetailsViewModel()
    }
    viewModel<AddOrEditPostViewModel> {
        DefaultAddOrEditPostViewModel()
    }
}
