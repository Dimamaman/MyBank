package com.example.mybank.ui.addCard

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybank.data.remote.request.AddCardRequest
import com.example.mybank.repository.CardRepository
import com.example.mybank.ui.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.gita.mobilebanking_gita.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val cardApiRepository: CardRepository,
    private val navigationHelper: NavigationHelper,
) : ViewModel() {

    private var nameBoolean = false
    private var numberBoolean = false
    private var dataBoolean = false

    private val _enabledButton = MutableStateFlow<Boolean>(false)
    val enabledButton: StateFlow<Boolean> = _enabledButton

    fun sendName(name: String) {
        nameBoolean = name.length > 2
        _enabledButton.tryEmit(nameBoolean && numberBoolean && dataBoolean)
    }

    fun sendNumber(number: String) {
        numberBoolean = number.length == 16
        _enabledButton.tryEmit(nameBoolean && numberBoolean && dataBoolean)
    }

    fun sendData(data: String) {
        dataBoolean = data.length == 4
        _enabledButton.tryEmit(nameBoolean && numberBoolean && dataBoolean)
    }

    fun addCard(addCard: AddCardRequest) {
//        cardApiRepository.addCard(addCard)
//            .onEach {
//                when (it) {
//                    is ResultData.Success -> {
//                        navigationHelper.navigateTo(AddCardDirections.actionAddCardToCardAddedSuccessfully())
//                    }
//                    is ResultData.Error -> {
//
//                    }
//                }
//            }

        viewModelScope.launch {
            cardApiRepository.addCard(addCard).collect {
                when(it) {
                    is ResultData.Success -> {
                        navigationHelper.navigateTo(AddCardDirections.actionAddCardToCardAddedSuccessfully())
                    }
                    is  ResultData.Error -> {

                    }
                }
            }
        }
    }
}