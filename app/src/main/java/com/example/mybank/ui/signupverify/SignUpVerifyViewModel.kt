package com.example.mybank.ui.signupverify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SignUpVerifyViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val navigationHelper: NavigationHelper
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _mistakeTv = MutableStateFlow<String?>(null)
    val mistakeTv: StateFlow<String?> = _mistakeTv

    fun signUpVerify(token: String, code: String) {
        _loading.tryEmit(true)
        authRepository.signUpVerify(token, code)
            .onEach {
                _loading.tryEmit(false)
                when (it) {
                    is ResultData.Success -> {
                        authRepository.disableFirstLaunch()
                        authRepository.setAsSigned()
                        navigationHelper.navigateTo(SignUpVerifyDirections.actionLoginVerifyToHomeFragment())
                    }

                    is ResultData.Error -> {
                        _mistakeTv.emit("Code noto'g'ri kiritildi")
                    }
                }
            }.launchIn(viewModelScope)
    }
}