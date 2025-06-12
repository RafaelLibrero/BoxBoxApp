package com.boxbox.app.ui.season.tab.drivers

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.boxbox.app.R
import com.boxbox.app.databinding.ItemDriverBinding
import com.boxbox.app.domain.model.Driver

class DriversViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDriverBinding.bind(view)

    fun render(driver: Driver, position: Int) {
        with (binding) {
            tvPosition.text = position.toString()
            ivDriver.load(driver.imagen) { crossfade(true) }
            tvName.text = driver.driverName
            tvCarNumber.text = driver.carNumber.toString()
            ivFlag.load(driver.flag) { crossfade(true) }
            tvPoints.text = driver.points.toString()

            when (position) {
                1 -> tvPosition.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.gold))
                2 -> tvPosition.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.silver))
                3 -> tvPosition.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.bronze))
                else -> tvPosition.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.transparent))
            }
        }
    }
}