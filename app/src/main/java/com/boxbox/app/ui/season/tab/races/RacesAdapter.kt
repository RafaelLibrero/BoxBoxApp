package com.boxbox.app.ui.season.tab.races

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.Race

class RacesAdapter(
    private var racesList: List<Race> = mutableListOf()
) : RecyclerView.Adapter<RacesViewHolder>() {

    fun updateList(list: List<Race>) {
        racesList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacesViewHolder {
        return RacesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_race, parent, false)
        )
    }

    override fun getItemCount() = racesList.size

    override fun onBindViewHolder(holder: RacesViewHolder, position: Int) {
        holder.render(racesList[position])
    }
}