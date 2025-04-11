package com.boxbox.app.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.databinding.ItemTopicBinding
import com.boxbox.app.domain.model.VTopic

class TopicViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding =  ItemTopicBinding.bind(view)

    fun render(topic: VTopic) {
        binding.tvTitle.text = topic.title
    }
}