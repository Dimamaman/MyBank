package com.example.mybank.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybank.data.remote.request.SignUpRequest
import com.example.mybank.repository.AuthRepository
import com.example.mybank.ui.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.mobilebanking_gita.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val navController: NavigationHelper
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    var loading: StateFlow<Boolean> = _loading

    private val _message = MutableStateFlow<String?>(null)
    var message: StateFlow<String?> = _message


    fun register(
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        phoneNumber: String,
        password: String,
        gender: String
    ) {
        _loading.tryEmit(true)
        val response = SignUpRequest(firstName,lastName,dateOfBirth,gender,phoneNumber,password,)
        authRepository.register(response)
            .onEach {
                _loading.emit(false)
                when(it) {
                    is ResultData.Success -> {
                        navController.navigateTo(SignUpDirections.actionSignUpToLoginVerify(phoneNumber,
                            it.data!!.token))
                    }
                    is ResultData.Error -> {
                        _message.emit(it.message)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}