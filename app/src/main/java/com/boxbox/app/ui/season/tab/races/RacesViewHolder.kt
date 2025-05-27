package com.boxbox.app.ui.season.tab.races

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.databinding.ItemRaceBinding
import com.boxbox.app.domain.model.Race
import com.boxbox.app.utils.DateFormatter
import com.squareup.picasso.Picasso

class RacesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemRaceBinding.bind(view)

    fun render(race: Race) {
        with (binding) {
            tvName.text = itemView.context.getString(R.string.race_name, race.raceName)
            Picasso.get().load(race.image).into(ivRace)
            tvDate.text = DateFormatter.raceFormat(race)
        }
    }


}