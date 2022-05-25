package com.example.proto.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.proto.R
import com.example.proto.databinding.FragmentAddOrEditPostBinding
import com.example.proto.extensions.bind
import com.example.proto.extensions.initToolbar
import com.example.proto.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AddOrEditPostFragment : Fragment(R.layout.fragment_add_or_edit_post) {

    private val binding by viewBinding(FragmentAddOrEditPostBinding::bind)
    private val args: AddOrEditPostFragmentArgs by navArgs()
    private val viewModel: AddOrEditPostViewModel by viewModel {
        parametersOf(args.post, args.user)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.render()
        viewModel.connect()
        viewModel.fetchPost()
    }

    private fun FragmentAddOrEditPostBinding.render() {
        initToolbar(
            toolbar = toolbar,
            menu = R.menu.add_or_edit_post,
            actionId = R.id.submitMenuItem
        ) {
            viewModel.updatePost(
                binding.titleTextInputLayout.editText?.text.toString(),
                binding.bodyTextInputLayout.editText?.text.toString()
            )
            // TODO: add error/result handling to navigate backstack conditionally
            findNavController().popBackStack(R.id.feedFragment, true)
        }
        if (args.post != -1L) {
            toolbar.title = getString(R.string.edit_post_fragment_label)
        } else {
            toolbar.title = getString(R.string.add_post_fragment_label)
        }
    }

    private fun AddOrEditPostViewModel.connect() {
        bind(post) { post ->
            binding.titleTextInputLayout.editText?.setText(post?.title)
            binding.bodyTextInputLayout.editText?.setText(post?.body)
        }
    }
}
