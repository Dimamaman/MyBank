package com.example.mybank.data.remote.request

data class SignUpVerifyRequest(
    val token: String,
    val code: String,
)
