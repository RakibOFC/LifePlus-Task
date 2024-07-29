package com.rakibofc.lifeplustask.di

import com.rakibofc.lifeplustask.data.local.LifePlusDao
import com.rakibofc.lifeplustask.data.remote.ApiService
import com.rakibofc.lifeplustask.data.repository.MainRepository
import com.rakibofc.lifeplustask.domain.usecase.MainUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(lifePlusDao: LifePlusDao, apiService: ApiService): MainUseCase {
        return MainRepository(lifePlusDao, apiService)
    }
}