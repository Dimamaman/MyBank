package com.example.mybank.data.remote.api

import com.example.mybank.data.remote.request.SignInVerifyRequest
import com.example.mybank.data.remote.request.SignUpRequest
import com.example.mybank.data.remote.request.SignUpVerifyRequest
import com.example.mybank.data.remote.response.SignInResponse
import com.example.mybank.data.remote.response.SignInVerifyResponse
import com.example.mybank.data.remote.response.SignUpResponse
import com.example.mybank.data.remote.response.SignUpVerifyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.mobilebanking_gita.data.remote.request.SignInRequest

interface AuthApi {
    @POST("auth/sign-in")
    suspend fun signIn(@Body data: SignInRequest) : Response<SignInResponse>

    @POST("auth/sign-up")
    suspend fun signUp(@Body data: SignUpRequest): Response<SignUpResponse>

    @POST("auth/sign-up/verify")
    suspend fun signUpVerify(
        @Body signUpVerifyData: SignUpVerifyRequest
    ): Response<SignUpVerifyResponse>

    @POST("auth/sign-in")
    suspend fun signInVerify(
        @Body signInVerify: SignInVerifyRequest
    ): Response<SignInVerifyResponse>
}