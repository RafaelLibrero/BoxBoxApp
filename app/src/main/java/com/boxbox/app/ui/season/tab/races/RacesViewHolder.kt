package com.boxbox.app.ui.season.tab.races

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.boxbox.app.R
import com.boxbox.app.databinding.ItemRaceBinding
import com.boxbox.app.domain.model.Race
import com.boxbox.app.utils.DateFormatter

class RacesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemRaceBinding.bind(view)

    fun render(race: Race, isNext: Boolean) {
        with (binding) {
            tvName.text = itemView.context.getString(R.string.race_name, race.raceName)
            ivRace.load(race.image) { crossfade(true) }
            tvDate.text = DateFormatter.raceFormat(race)

            if (isNext) {
                tvNextRaceLabel.visibility = View.VISIBLE
            } else {
                tvNextRaceLabel.visibility = View.GONE
            }
        }


    }


}