package com.example.mybank.data.remote.request

data class SignInVerifyRequest(
    val token: String,
    val code: String
)
