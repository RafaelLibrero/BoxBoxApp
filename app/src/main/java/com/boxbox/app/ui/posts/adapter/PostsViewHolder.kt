package com.boxbox.app.ui.posts.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.databinding.ItemPostBinding
import com.boxbox.app.domain.model.Post
import com.boxbox.app.utils.DateFormatter

class PostsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemPostBinding.bind(view)

    fun render(post: Post) {
        with (binding) {
            tvText.text = post.text
            tvCreatedAt.text = DateFormatter.formatToMinutes(post.createdAt)
        }
    }
}