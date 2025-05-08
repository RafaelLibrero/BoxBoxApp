package com.boxbox.app.ui.season.tab.drivers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.databinding.ItemDriverBinding
import com.boxbox.app.domain.model.Driver
import com.squareup.picasso.Picasso

class DriversViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDriverBinding.bind(view)

    fun render(driver: Driver, position: Int) {
        binding.tvPosition.text = position.toString()
        Picasso.get().load(driver.imagen).into(binding.ivDriver)
        binding.tvName.text = driver.driverName
        binding.tvCarNumber.text = driver.carNumber.toString()
        Picasso.get().load(driver.flag).into(binding.ivFlag)
        binding.tvPoints.text = driver.points.toString()
    }
}