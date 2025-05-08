package com.boxbox.app.ui.season.tab.races

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.databinding.ItemRaceBinding
import com.boxbox.app.domain.model.Race
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class RacesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemRaceBinding.bind(view)

    fun render(race: Race) {
        with (binding) {
            tvName.text = itemView.context.getString(R.string.race_name, race.raceName)
            Picasso.get().load(race.image).into(ivRace)
            tvDate.text = formatDates(race)
        }
    }

    private fun formatDates(race: Race): String {
        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())

        val dayStart = dayFormat.format(race.startDate)
        val dayEnd = dayFormat.format(race.endDate)
        val month = monthFormat.format(race.endDate)

        val displayText = "$dayStart - $dayEnd $month"

        return displayText
    }
}