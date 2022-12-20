package com.example.mybank.data.remote.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("first-name") val firstName: String,
    @SerializedName("last-name") val lastName: String,
    @SerializedName("born-date") val bornDate: String,
    val gender: String,
    val phone: String,
    val password: String
)