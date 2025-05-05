package com.boxbox.app.data.network.response

import com.boxbox.app.domain.model.Team
import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("teamID") val teamID: Int,
    @SerializedName("teamName") val teamName: String,
    @SerializedName("logo") val logo: String
) {
    fun toDomain(): Team {
        return Team(
            teamID = teamID,
            teamName = teamName,
            logo = logo
        )
    }
}