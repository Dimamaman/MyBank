package com.example.mybank.repository

import com.example.mybank.data.local.LocalStorage
import com.example.mybank.data.remote.api.CardApi
import com.example.mybank.data.remote.api.HomeApi
import com.example.mybank.data.remote.response.GetTotalBalanceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.mobilebanking_gita.utils.ResultData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val homeApi: HomeApi,
    private val localStorage: LocalStorage,
    private val cardApi: CardApi,

    ) {
    fun getTotalBalance(): Flow<ResultData<GetTotalBalanceResponse?>> = flow<ResultData<GetTotalBalanceResponse?>> {
        val response = homeApi.getTotalBalance(localStorage.token)

        if (response.isSuccessful) {
            if (response.body() != null) {
                emit(ResultData.Success(response.body()))
            } else {
                emit(ResultData.Error(response.message()))
            }
        } else {
            emit(ResultData.Error(response.message()))
        }

    }.flowOn(Dispatchers.IO)

//    fun getAllCards(): kotlinx.coroutines.flow.Flow<ResultData<List<CardResponse>?>> = flow<ResultData<List<CardResponse>?>> {
//        val response = cardApi.getCards(localStorage.token)
//
//        if (response.isSuccessful) {
//            if (response.body() != null) {
//                emit(ResultData.Success(response.body()))
//            } else {
//                emit(ResultData.Error(response.message()))
//            }
//        } else {
//            emit(ResultData.Error(response.message()))
//        }
//
//    }.flowOn(Dispatchers.IO)
}