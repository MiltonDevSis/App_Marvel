package com.mpfcoding.app_marvel.framework.di

import com.mpfcoding.app_marvel.framework.FavoritesRepositoryImpl
import com.mpfcoding.app_marvel.framework.local.RoomFavoritesDataSource
import com.mpfcoding.core.data.repository.FavoritesLocalDataSource
import com.mpfcoding.core.data.repository.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavoriteRepositoryModule {

    @Binds
    fun bindFavoriteRepository(repository: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    fun bindLocalDataSource(datasource: RoomFavoritesDataSource): FavoritesLocalDataSource
}