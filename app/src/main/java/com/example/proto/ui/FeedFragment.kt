package com.example.proto.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.proto.R
import com.example.proto.databinding.FragmentFeedBinding
import com.example.proto.extensions.initToolbar
import com.example.proto.extensions.viewBinding
import com.example.proto.ui.adapters.PostAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val binding by viewBinding(FragmentFeedBinding::bind)
    private val args: FeedFragmentArgs by navArgs()
    private val viewModel: FeedViewModel by viewModel { parametersOf(args.user) }

    private val adapter by lazy {
        PostAdapter(
            onPostClickListener = {
                findNavController().navigate(FeedFragmentDirections.toPostDetails(post = it))
            },
            onProfileClickListener = {
                findNavController().navigate(FeedFragmentDirections.toUserFeed(user = it))
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.render()
        viewModel.connect()
    }

    private fun FragmentFeedBinding.render() {
        toolbar.title = getString(R.string.feed_fragment_label)
        recyclerView.adapter = adapter

        newPostFAB.setOnClickListener {
            findNavController().navigate(FeedFragmentDirections.toAddOrEditPost())
        }

        // TODO: let VM decide if the toolbar is to be shown depending on the current user
        updateToolbar(false)
    }

    private fun FeedViewModel.connect() { }

    private fun updateToolbar(isCurrentUser: Boolean) {
        if (args.user != -1L && isCurrentUser) {
            initToolbar(
                toolbar = binding.toolbar,
                menu = R.menu.user_feed,
                actionId = R.id.refreshMenuItem
            ) {
                // TODO: update needs_sync for all the posts
            }
        } else if (args.user != -1L) {
            initToolbar(binding.toolbar)
        }
    }
}
