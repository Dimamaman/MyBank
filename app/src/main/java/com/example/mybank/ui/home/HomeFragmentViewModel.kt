package com.example.mybank.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybank.data.remote.response.CardResponse
import com.example.mybank.repository.CardRepository
import com.example.mybank.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.gita.mobilebanking_gita.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val cardRepository: CardRepository
) : ViewModel() {
    private val _getTotalBalance = MutableStateFlow<Int>(0)
    val getAllBalance: StateFlow<Int> = _getTotalBalance

    private val _getAllCards = MutableSharedFlow<List<CardResponse>>()
    var getAllCards: SharedFlow<List<CardResponse>> = _getAllCards


    init {
        viewModelScope.launch {
            homeRepository.getTotalBalance()
                .onEach {
                    when(it) {
                        is ResultData.Success -> {
                            it.data?.let { it1 -> _getTotalBalance.tryEmit(it1.totalBalance) }
                        }
                        is ResultData.Error -> {

                        }
                    }
                }
        }
        viewModelScope.launch {
            cardRepository.getAllCards()
                .onEach {
                    cardRepository.getAllCards().collect() {
                        when(it) {
                            is ResultData.Success -> {
                                _getAllCards.emit(it.data!!)
                            }
                            is ResultData.Error ->{

                            }
                        }
                    }
                }
        }
    }

//    val cards: MutableSharedFlow<List<CardItemResponse>> = MutableSharedFlow()
//
//
//    fun allCards() {
//        viewModelScope.launch {
//            homeRepository.getAllCards().collect {
//                when (it) {
//                    is ResultData.Error -> {
//                        Log.d("HHH", "xatolik")
//                    }
//                    is ResultData.Success -> {
//                        cards.emit(it.data)
//                    }
//                }
//            }
//        }
//    }
}