package com.boxbox.app.data.network.response

import com.boxbox.app.domain.model.Team
import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teamId") val teamId: Int,
    @SerializedName("teamName") val teamName: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("points") val points: Int
) {
    fun toDomain(): Team {
        return Team(
            teamId = teamId,
            teamName = teamName,
            logo = logo,
            points = points
        )
    }
}