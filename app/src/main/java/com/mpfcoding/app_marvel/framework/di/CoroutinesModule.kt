package com.mpfcoding.app_marvel.framework.di

import com.mpfcoding.core.usecase.base.AppCoroutinesDispatchers
import com.mpfcoding.core.usecase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    @Provides
    fun bindDispatcherProvider(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}