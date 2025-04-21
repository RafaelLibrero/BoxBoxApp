package com.boxbox.app.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.databinding.ItemTopicBinding
import com.boxbox.app.domain.model.VTopic

class TopicViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemTopicBinding.bind(view)

    fun render(topic: VTopic) {
        binding.tvTitle.text = topic.title
        binding.tvDescription.text = topic.description
        binding.tvConversations.text =
            binding.tvConversations.context.getString(R.string.conversations, topic.conversations)
        binding.tvPosts.text =
            binding.tvPosts.context.getString(R.string.posts, topic.posts)
    }
}