package com.boxbox.app.data.network.response

import com.boxbox.app.data.network.DateJsonAdapter
import com.boxbox.app.domain.model.Race
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.Date

data class RaceResponse(
    @SerializedName("raceID") val raceID: Int,
    @SerializedName("raceName") val raceName: String,
    @SerializedName("image") val image: String,
    @SerializedName("location") val location: String,
    @JsonAdapter(DateJsonAdapter::class) @SerializedName("startDate") val startDate: Date,
    @JsonAdapter(DateJsonAdapter::class) @SerializedName("endDate") val endDate: Date,
    @SerializedName("winnerDriverId") val winnerDriverId: Int
) {
    fun toDomain(): Race {
        return Race(
            raceID = raceID,
            raceName = raceName,
            image = image,
            location = location,
            startDate = startDate,
            endDate = endDate,
            winnerDriverId = winnerDriverId
        )
    }
}