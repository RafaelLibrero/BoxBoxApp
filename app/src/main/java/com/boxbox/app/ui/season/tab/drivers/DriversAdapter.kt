package com.boxbox.app.ui.season.tab.drivers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.Driver

class DriversAdapter(
    private var driversList: List<Driver> = mutableListOf()
) : RecyclerView.Adapter<DriversViewHolder>() {

    fun updateList(list: List<Driver>) {
        driversList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversViewHolder {
        return DriversViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_driver, parent, false)
        )
    }

    override fun getItemCount() = driversList.size

    override fun onBindViewHolder(holder: DriversViewHolder, position: Int) {
        holder.render(driversList[position])
    }

}