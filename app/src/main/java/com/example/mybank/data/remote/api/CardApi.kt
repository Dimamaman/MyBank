package com.example.mybank.data.remote.api

import com.example.mybank.data.remote.request.AddCardRequest
import com.example.mybank.data.remote.response.AddCardResponse
import com.example.mybank.data.remote.response.CardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CardApi {
    @GET("card")
    suspend fun getCards(
        @Header("Authorization") token: String
    ): Response<List<CardResponse>>

    @POST("card")
    suspend fun addCard(
        @Header("Authorization") accessToken: String,
        @Body addCard: AddCardRequest
    ): Response<AddCardResponse>
}