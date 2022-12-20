package com.example.mybank.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.mobilebanking_gita.utils.SharedPreferenceHelper
import javax.inject.Inject

class LocalStorage @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferenceHelper(context) {
    var isSigned:Boolean by booleans(false)
    var isFirstLaunch: Boolean by booleans(true)
    var token: String by strings()
    var accessToken: String by strings()
    var refreshToken: String by strings()
}