package com.boxbox.app.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://boxboxapi.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}