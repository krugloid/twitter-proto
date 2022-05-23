package com.example.proto.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.proto.R
import com.example.proto.databinding.FragmentPostDetailsBinding
import com.example.proto.extensions.initToolbar
import com.example.proto.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailsFragment : Fragment(R.layout.fragment_post_details) {

    private val binding by viewBinding(FragmentPostDetailsBinding::bind)
    private val args: PostDetailsFragmentArgs by navArgs()
    private val viewModel: PostDetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.render()
        viewModel.connect()
    }

    private fun FragmentPostDetailsBinding.render() {
        toolbar.title = getString(R.string.post_details_fragment_label)
        // TODO: fill out post details

        // TODO: show toolbar only if it's a current user
        initToolbar(
            toolbar = toolbar,
            menu = R.menu.post_details,
            actionId = R.id.editMenuItem
        ) {
            // TODO update DB
        }
    }

    private fun PostDetailsViewModel.connect() {

    }
}
