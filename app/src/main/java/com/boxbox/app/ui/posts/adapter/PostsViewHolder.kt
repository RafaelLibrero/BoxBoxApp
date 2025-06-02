package com.boxbox.app.ui.posts.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.databinding.ItemPostBinding
import com.boxbox.app.domain.model.PostWithUser
import com.boxbox.app.utils.DateFormatter
import com.squareup.picasso.Picasso

class PostsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemPostBinding.bind(view)

    fun render(postWithUser: PostWithUser) {

        val post = postWithUser.post
        val user = postWithUser.user

        with (binding) {
            tvUsername.text = user!!.userName
            Picasso.get().load(user.profilePicture).into(ivAvatar)
            tvText.text = post.text
            tvCreatedAt.text = DateFormatter.formatToMinutes(post.createdAt)
        }
    }
}