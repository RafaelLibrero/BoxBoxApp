package com.boxbox.app.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.VTopic

class TopicAdapter(
    private var topicsList: List<VTopic> = emptyList()
) : RecyclerView.Adapter<TopicViewHolder>() {

    fun updateList(list: List<VTopic>) {
        topicsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        )
    }

    override fun getItemCount() = topicsList.size

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.render(topicsList[position])
    }

}