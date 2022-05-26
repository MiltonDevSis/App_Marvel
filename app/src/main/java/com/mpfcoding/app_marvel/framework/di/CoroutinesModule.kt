package com.mpfcoding.app_marvel.framework.di

import com.mpfcoding.core.usecase.base.AppCoroutinesDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    fun provideDispatcherProvider() = AppCoroutinesDispatchers(
        Dispatchers.IO,
        Dispatchers.Default,
        Dispatchers.Main
    )
}