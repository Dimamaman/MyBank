package com.example.mybank.repository

import com.example.mybank.data.local.LocalStorage
import com.example.mybank.data.remote.api.CardApi
import com.example.mybank.data.remote.request.AddCardRequest
import com.example.mybank.data.remote.response.AddCardResponse
import com.example.mybank.data.remote.response.CardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.mobilebanking_gita.utils.ResultData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepository @Inject constructor(
    private val cardApi: CardApi,
    private val localStorage: LocalStorage
) {
    fun getAllCards(): Flow<ResultData<List<CardResponse>?>> = flow<ResultData<List<CardResponse>?>> {
        val response = cardApi.getCards("Bearer " + localStorage.token)

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

    fun addCard(addCard: AddCardRequest): Flow<ResultData<AddCardResponse?>> = flow {
        val response = cardApi.addCard(localStorage.accessToken,addCard)

        if (response.isSuccessful) {
            if (response.body() != null) {
                emit(ResultData.Success(response.body()))
            }else {
                emit(ResultData.Error(response.message()))
            }
        } else {
            emit(ResultData.Error(response.message()))
        }
    }.flowOn(Dispatchers.IO)
}