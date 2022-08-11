package com.mpfcoding.app_marvel.framework.di

import com.mpfcoding.app_marvel.framework.StorageRepositoryImpl
import com.mpfcoding.app_marvel.framework.local.DataStorePreferencesDataSource
import com.mpfcoding.core.data.repository.StorageLocalDataSource
import com.mpfcoding.core.data.repository.StorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface StorageRepositoryModule {

    @Binds
    fun bindStorageRepository(repository: StorageRepositoryImpl): StorageRepository

    @Binds
    fun bindLocalDataSouce(dataSource: DataStorePreferencesDataSource): StorageLocalDataSource
}