package com.example.mybank.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.mybank.data.remote.api.AuthApi
import com.example.mybank.data.remote.api.CardApi
import com.example.mybank.data.remote.api.HomeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getOkHTTPClient(@ApplicationContext context: Context) : OkHttpClient =  OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor(context))
        .build()


    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://143.198.48.222:84/v1/mobile-bank/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun authProvider(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun homeApiProvider(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun cardApiProvider(retrofit: Retrofit): CardApi = retrofit.create(CardApi::class.java)


}