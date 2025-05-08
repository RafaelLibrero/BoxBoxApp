package com.boxbox.app.data.network.response

import com.boxbox.app.domain.model.Driver
import com.google.gson.annotations.SerializedName

data class DriverResponse(
    @SerializedName("driverID") val driverID: Int,
    @SerializedName("driverName") val driverName: String,
    @SerializedName("carNumber") val carNumber: Int,
    @SerializedName("teamID") val teamID: Int,
    @SerializedName("flag") val flag: String,
    @SerializedName("imagen") val imagen: String,
    @SerializedName("points") val points: Int
) {
    fun toDomain(): Driver {
        return Driver(
            driverID = driverID,
            driverName = driverName,
            carNumber = carNumber,
            teamID = teamID,
            flag = flag,
            imagen = imagen,
            points = points
        )
    }
}