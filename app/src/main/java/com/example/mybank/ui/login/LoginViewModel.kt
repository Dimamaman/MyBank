package com.example.mybank.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybank.repository.AuthRepository
import com.example.mybank.ui.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.gita.mobilebanking_gita.utils.ResultData
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val navigationHelper: NavigationHelper
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    private var phoneBoolean = false
    private var passwordBoolean = false
    private var phoneNumber = ""
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    private val _enableButton = MutableStateFlow<Boolean>(false)
    val enableButton: StateFlow<Boolean> = _enableButton

    fun sendPhone(phone: String) {
        phoneBoolean = phone.length == 13 && phone.startsWith("+998")
        phoneNumber = phone
        _enableButton.tryEmit(phoneBoolean && passwordBoolean)
    }

    fun sendPassword(password: String) {
        passwordBoolean = password.length in 5..20
        _enableButton.tryEmit(phoneBoolean && passwordBoolean)
    }

    fun login(phone: String, password: String) {
        if (phone.isEmpty()) {
            _message.tryEmit("Phone kiritilmagan")
            return
        }

        if (password.length !in 5..20) {
            _message.tryEmit("Parol uzunligi shartlarga mos kelmagan")
            return
        }

        viewModelScope.launch {
            authRepository.login(phone, password).collect{
                when(it){
                    is ResultData.Error -> {
                        Log.d("ttt", "viewModel error - ${it.message}")
                    }
                    is ResultData.Success -> {
                        navigationHelper.navigateTo(LoginScreenDirections.actionLoginScreenToLoginVerify(phoneNumber,it.data!!.token))
                    }
                }
            }
        }



//        _loading.tryEmit(true)
//        authRepository.login(phone, password)
//            .onEach {
//                _loading.emit(false)
//                when (it) {
//                    is ResultData.Success -> {
//                        navigationHelper.navigateTo(LoginScreenDirections.actionLoginScreenToHiltLoginVerify(phoneNumber))
//                    }
//                    is ResultData.Error -> _message.emit(it.message)
//                }
//            }
//            .launchIn(viewModelScope)
    }

    fun openRegister(){
        viewModelScope.launch {
            navigationHelper.navigateTo(LoginScreenDirections.actionLoginScreenToSignUp())
        }
    }
}