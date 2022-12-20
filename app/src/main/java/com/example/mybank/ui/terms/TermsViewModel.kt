package com.example.mybank.ui.terms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybank.repository.AuthRepository
import com.example.mybank.ui.NavigationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val navigationHelper: NavigationHelper
) : ViewModel(){
    fun accept(){
        viewModelScope.launch {
            navigationHelper.navigateTo(TermsScreenDirections.actionTermsScreenToSignUp())
        }
    }
}