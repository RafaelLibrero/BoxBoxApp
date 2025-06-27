package com.boxbox.app.ui.posts.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.boxbox.app.databinding.ItemPostBinding
import com.boxbox.app.domain.model.PostWithUser
import com.boxbox.app.utils.DateFormatter

class PostsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemPostBinding.bind(view)

    fun render(postWithUser: PostWithUser, onUserSelected: (Int) -> Unit) {

        val post = postWithUser.post
        val user = postWithUser.user

        with (binding) {
            tvUsername.text = user!!.userName
            ivAvatar.load(user.profilePicture) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvText.text = post.text
            tvCreatedAt.text = DateFormatter.getCreatedAtText(post.createdAt!!)

            ivAvatar.setOnClickListener {
                onUserSelected(user.userId)
            }
        }
    }
}