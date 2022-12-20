package com.example.mybank.repository

import android.util.Log
import com.example.mybank.data.local.LocalStorage
import com.example.mybank.data.remote.api.AuthApi
import com.example.mybank.data.remote.request.SignInVerifyRequest
import com.example.mybank.data.remote.request.SignUpRequest
import com.example.mybank.data.remote.request.SignUpVerifyRequest
import com.example.mybank.data.remote.response.SignInResponse
import com.example.mybank.data.remote.response.SignInVerifyResponse
import com.example.mybank.data.remote.response.SignUpResponse
import com.example.mybank.data.remote.response.SignUpVerifyResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.mobilebanking_gita.data.remote.request.SignInRequest
import uz.gita.mobilebanking_gita.utils.ResultData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val localStorage: LocalStorage,
    private val authApi: AuthApi
) {
    fun isFirstLaunch(): Boolean = localStorage.isFirstLaunch

    fun isSinged(): Boolean = localStorage.isSigned

    fun disableFirstLaunch() {
        localStorage.isFirstLaunch = false
    }

    fun setAsSigned(){
        localStorage.isSigned = true
    }

    fun login(phone: String, password: String): Flow<ResultData<SignInResponse?>> = flow {
        val request = SignInRequest(phone, password)
        val response = authApi.signIn(request)

        if (response.isSuccessful) {
            if (response.body() != null) {
                emit(ResultData.Success(response.body()))
            } else {
                emit(ResultData.Error(response.message()))
            }
        } else {
            emit(ResultData.Error(response.message()))
        }
    }

    fun register(request: SignUpRequest): Flow<ResultData<SignUpResponse?>> = flow {
        Log.d("TTT", request.toString().toString())
        val response = authApi.signUp(request)
        Log.d("TTT", response.code().toString())
        if (response.isSuccessful) {
            if (response.body() != null) {
                emit(ResultData.Success(response.body()))
            } else {
                emit(ResultData.Error(response.message()))
            }
        } else {
            emit(ResultData.Error(response.message()))
        }
    }

    fun signUpVerify(token: String, code: String): Flow<ResultData<SignUpVerifyResponse?>> = flow {
        val request = SignUpVerifyRequest(token,code)
        val response = authApi.signUpVerify(request)

        if (response.isSuccessful) {
            if (response.body() != null) {
                localStorage.token = response.body()!!.accessToken
                localStorage.accessToken = response.body()!!.accessToken
                localStorage.refreshToken = response.body()!!.refreshToken

                emit(ResultData.Success(response.body()))
            } else {
                emit(ResultData.Error(response.message()))
            }
        } else {
            emit(ResultData.Error(response.message()))
        }
    }.flowOn(IO)

    fun signInVerify(token: String,code: String): Flow<ResultData<SignInVerifyResponse?>> = flow {
        val request = SignInVerifyRequest(token,code)
        val response = authApi.signInVerify(request)

        if (response.isSuccessful) {
            if (response.body() != null) {
                emit(ResultData.Success(response.body()))
            } else {
                emit(ResultData.Error(response.message()))
            }
        } else {
            emit(ResultData.Error(response.message()))
        }
    }.flowOn(IO)

}