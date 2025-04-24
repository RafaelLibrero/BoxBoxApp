package com.boxbox.app.data.network

import android.content.Context
import com.boxbox.app.data.repository.AuthRepositoryImp
import com.boxbox.app.data.repository.RepositoryImp
import com.boxbox.app.domain.repository.AuthRepository
import com.boxbox.app.domain.repository.Repository
import com.tuapp.data.storage.TokenStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): Repository {
        return RepositoryImp(apiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService, tokenStorage: TokenStorage): AuthRepository {
        return AuthRepositoryImp(apiService, tokenStorage)
    }

    @Provides
    @Singleton
    fun provideTokenStorage(@ApplicationContext context: Context): TokenStorage {
        return TokenStorage(context)
    }
}