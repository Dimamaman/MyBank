package com.example.mybank.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybank.repository.AuthRepository
import com.example.mybank.ui.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val navigationHelper: NavigationHelper
) : ViewModel() {
    init {
        viewModelScope.launch {
            delay(2000)
            when {
                authRepository.isFirstLaunch() -> {
                    navigationHelper.navigateTo(SplashScreenDirections.actionSplashScreenToTermsScreen())
                }
                authRepository.isSinged() -> {
                    navigationHelper.navigateTo(SplashScreenDirections.actionSplashScreenToHomeFragment())
                }
                else -> { /*Login*/
                    navigationHelper.navigateTo(SplashScreenDirections.actionSplashScreenToLoginScreen())
                }
            }
        }
    }

    fun demo(){

    }
}