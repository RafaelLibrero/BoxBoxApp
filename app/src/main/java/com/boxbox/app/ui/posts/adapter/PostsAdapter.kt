package com.boxbox.app.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.PostWithUser

class PostsAdapter (
    private var postsList: List<PostWithUser> = mutableListOf(),
    private var onUserSelected: (Int) -> Unit
) : RecyclerView.Adapter<PostsViewHolder>() {

    fun updateList(list: List<PostWithUser>, onComplete: () -> Unit) {
        postsList = list
        notifyDataSetChanged()
        onComplete.invoke()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PostsViewHolder,
        position: Int
    ) {
        holder.render(postsList[position], onUserSelected)
    }

    override fun getItemCount() = postsList.size
}