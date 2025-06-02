package com.boxbox.app.ui.season.tab.races

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.Race
import java.time.LocalDateTime
import java.time.ZoneId

class RacesAdapter(
    private var racesList: List<Race> = mutableListOf()
) : RecyclerView.Adapter<RacesViewHolder>() {

    private var nextRaceIndex: Int = -1

    fun updateList(list: List<Race>) {
        racesList = list
        nextRaceIndex = findNextRaceIndex(racesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacesViewHolder {
        return RacesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_race, parent, false)
        )
    }

    override fun getItemCount() = racesList.size

    override fun onBindViewHolder(holder: RacesViewHolder, position: Int) {
        val isNext = position == nextRaceIndex
        holder.render(racesList[position], isNext)
    }

    fun findNextRaceIndex(races: List<Race>): Int {
        val now = LocalDateTime.now()
        return races.indexOfFirst { race ->
            val raceEnd = race.endDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            raceEnd.isAfter(now)
        }.takeIf { it >= 0 } ?: -1
    }

}