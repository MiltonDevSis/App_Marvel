package com.mpfcoding.app_marvel.framework.di

import com.mpfcoding.core.usecase.GetCharactersUseCase
import com.mpfcoding.core.usecase.GetCharactersUseCaseImpl
import com.mpfcoding.core.usecase.GetComicsUseCase
import com.mpfcoding.core.usecase.GetComicsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    // vai retornar oque?? oque estiver no parametro
    // para qual interface?? oque estiver no retorno

    @Binds
    fun bindGetCharactersUseCase(useCase: GetCharactersUseCaseImpl): GetCharactersUseCase

    @Binds
    fun bindGetComicsUseCase(useCase: GetComicsUseCaseImpl): GetComicsUseCase
}