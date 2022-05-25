package com.example.proto.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.proto.R
import com.example.proto.databinding.FragmentPostDetailsBinding
import com.example.proto.extensions.bind
import com.example.proto.extensions.initToolbar
import com.example.proto.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PostDetailsFragment : Fragment(R.layout.fragment_post_details) {

    private val binding by viewBinding(FragmentPostDetailsBinding::bind)
    private val args: PostDetailsFragmentArgs by navArgs()
    private val viewModel: PostDetailsViewModel by viewModel { parametersOf(args.post, args.user) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.render()
        viewModel.connect()
        viewModel.refresh()
    }

    private fun FragmentPostDetailsBinding.render() {
        toolbar.title = getString(R.string.post_details_fragment_label)
    }

    private fun PostDetailsViewModel.connect() {
        bind(isLoading) { binding.loading.isVisible = it }
        bind(post) { post ->
            binding.titleTextView.text = post?.title
            binding.bodyTextView.text = post?.body
            if (post?.image?.isNotEmpty() != false) {
                binding.postImageView.load(post?.image)
            }
        }
        bind(isCurrentUser) { updateToolbar(it) }
    }

    private fun updateToolbar(isCurrentUser: Boolean) {
        if (isCurrentUser) {
            initToolbar(
                toolbar = binding.toolbar,
                menu = R.menu.post_details,
                actionId = R.id.editMenuItem
            ) {
                findNavController().navigate(
                    PostDetailsFragmentDirections.toAddOrEditPost(
                        post = args.post,
                        user = args.user
                    )
                )
            }
        } else {
            initToolbar(binding.toolbar)
        }
    }
}
