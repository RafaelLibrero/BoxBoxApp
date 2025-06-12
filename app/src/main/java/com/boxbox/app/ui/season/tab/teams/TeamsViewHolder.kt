package com.boxbox.app.ui.season.tab.teams

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.boxbox.app.R
import com.boxbox.app.databinding.ItemTeamBinding
import com.boxbox.app.domain.model.Team

class TeamsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemTeamBinding.bind(view)

    fun render(team: Team, position: Int) {
        with (binding) {
            tvPosition.text = position.toString()
            ivLogo.load(team.logo) { crossfade(true) }
            tvName.text = team.teamName
            tvPoints.text = team.points.toString()

            when (position) {
                1 -> tvPosition.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.gold))
                2 -> tvPosition.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.silver))
                3 -> tvPosition.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.bronze))
                else -> tvPosition.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.transparent))
            }
        }
    }
}