package com.mpfcoding.app_marvel.framework.di

import com.mpfcoding.app_marvel.framework.CharactersRepositoryImpl
import com.mpfcoding.app_marvel.framework.remote.RetrofitCharactersDataSource
import com.mpfcoding.core.data.repository.CharactersRemoteDataSource
import com.mpfcoding.core.data.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModel {

    @Binds
    fun bindCharactersRepository(repository: CharactersRepositoryImpl): CharactersRepository

    @Binds
    fun bindRemoteDataSource(datasource: RetrofitCharactersDataSource): CharactersRemoteDataSource
}