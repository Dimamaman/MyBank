package com.example.mybank.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInVerifyResponse(
    @SerializedName("refresh-token")
    val refreshToke: String,
    @SerializedName("access-token")
    val accessToken: String
)
