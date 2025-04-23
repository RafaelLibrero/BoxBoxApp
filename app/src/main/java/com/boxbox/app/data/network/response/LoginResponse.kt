package com.boxbox.app.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("response") val token: String
)
