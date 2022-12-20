package com.example.mybank.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignUpVerifyResponse(
    @SerializedName ("refresh-token")
    val refreshToken: String,
    @SerializedName("access-token")
    val accessToken: String
)
