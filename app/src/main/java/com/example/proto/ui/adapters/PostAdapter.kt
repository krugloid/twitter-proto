package com.example.proto.ui.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.proto.databinding.OnePostItemBinding
import com.example.proto.model.PostItem

typealias PostClickListener = (Long, Long) -> Unit
typealias ProfileClickListener = (Long) -> Unit

private val diffUtil = object : DiffUtil.ItemCallback<PostItem>() {
    override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean =
        oldItem.postId == newItem.postId
    override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem) = oldItem == newItem
}

class PostAdapter(
    private val onPostClickListener: PostClickListener,
    private val onProfileClickListener: ProfileClickListener
) : ListAdapter<PostItem, PostAdapter.PostViewHolder>(diffUtil) {

    inner class PostViewHolder(private val binding: OnePostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostItem) {
            with(binding) {
                titleTextView.text = item.title
                bodyTextView.text = item.body
                profileTextView.text = item.profileBadge
                // TODO: take care of the button states
                ViewCompat.setBackgroundTintList(profileView, ColorStateList.valueOf(item.color))
                profileView.setOnClickListener {
                    onProfileClickListener.invoke(item.userId)
                }
                root.setOnClickListener {
                    onPostClickListener.invoke(item.postId, item.userId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            OnePostItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}