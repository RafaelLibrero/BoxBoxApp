package com.boxbox.app.ui.season.tab.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.Team

class TeamsAdapter(
    private var teamsList: List<Team> = mutableListOf()
) : RecyclerView.Adapter<TeamsViewHolder>() {

    fun updateList(list: List<Team>) {
        teamsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        )
    }

    override fun getItemCount() = teamsList.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.render(teamsList[position])
    }
}