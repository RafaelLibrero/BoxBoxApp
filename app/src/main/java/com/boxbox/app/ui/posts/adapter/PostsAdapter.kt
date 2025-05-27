package com.boxbox.app.ui.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.Post

class PostsAdapter (
    private var postsList: List<Post> = mutableListOf()
) : RecyclerView.Adapter<PostsViewHolder>() {

    fun updateList(list: List<Post>) {
        postsList = list
        notifyDataSetChanged()
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
        holder.render(postsList[position])
    }

    override fun getItemCount() = postsList.size
}