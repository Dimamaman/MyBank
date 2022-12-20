package com.example.mybank.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetTotalBalanceResponse(
    @SerializedName("total-balance")
    val totalBalance: Int
)
