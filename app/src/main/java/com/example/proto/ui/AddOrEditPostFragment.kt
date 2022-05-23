package com.example.proto.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.proto.R
import com.example.proto.databinding.FragmentAddOrEditPostBinding
import com.example.proto.extensions.initToolbar
import com.example.proto.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddOrEditPostFragment : Fragment(R.layout.fragment_add_or_edit_post) {

    private val binding by viewBinding(FragmentAddOrEditPostBinding::bind)
    private val args: AddOrEditPostFragmentArgs by navArgs()
    private val viewModel: AddOrEditPostViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.render()
        viewModel.connect()
    }

    private fun FragmentAddOrEditPostBinding.render() {
        if (args.post != null) {
            toolbar.title = getString(R.string.edit_post_fragment_label)

//            titleTextInputLayout.editText?.setText(args.post!!.title)
//            bodyTextInputLayout.editText?.setText(args.post!!.title)

            initToolbar(
                toolbar = toolbar,
                menu = R.menu.add_or_edit_post,
                actionId = R.id.submitMenuItem
            ) {
                // TODO update DB
            }
        } else {
            toolbar.title = getString(R.string.add_post_fragment_label)
        }
        // TODO: check if a new post is complete and refresh toolbar
    }

    private fun AddOrEditPostViewModel.connect() {

    }
}
