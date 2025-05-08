package com.boxbox.app.ui.season.tab.teams

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.databinding.ItemTeamBinding
import com.boxbox.app.domain.model.Team
import com.squareup.picasso.Picasso

class TeamsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemTeamBinding.bind(view)

    fun render(team: Team, position: Int) {
        binding.tvPosition.text = position.toString()
        Picasso.get().load(team.logo).into(binding.ivLogo)
        binding.tvName.text = team.teamName
        binding.tvPoints.text = team.points.toString()
    }
}