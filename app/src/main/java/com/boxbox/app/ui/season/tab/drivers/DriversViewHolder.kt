package com.boxbox.app.ui.season.tab.drivers

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.databinding.ItemDriverBinding
import com.boxbox.app.domain.model.Driver
import com.squareup.picasso.Picasso

class DriversViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDriverBinding.bind(view)

    fun render(driver: Driver, position: Int) {
        with (binding) {
            tvPosition.text = position.toString()
            Picasso.get().load(driver.imagen).into(ivDriver)
            tvName.text = driver.driverName
            tvCarNumber.text = driver.carNumber.toString()
            Picasso.get().load(driver.flag).into(ivFlag)
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