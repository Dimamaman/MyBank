package com.example.mybank.data.remote.api

import com.example.mybank.data.remote.response.GetTotalBalanceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeApi {
    @GET("home/total-balance")
    suspend fun getTotalBalance(
        @Header("Authorization") token: String
    ): Response<GetTotalBalanceResponse>
}