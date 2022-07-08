package com.mpfcoding.app_marvel.framework.di

import com.mpfcoding.core.usecase.GetCharactersUseCase
import com.mpfcoding.core.usecase.GetCharactersUseCaseImpl
import com.mpfcoding.core.usecase.GetCharacterCategoriesUseCase
import com.mpfcoding.core.usecase.GetCharacterCategoriesUseCaseImpl
import com.mpfcoding.core.usecase.AddFavoriteUseCase
import com.mpfcoding.core.usecase.AddFavoriteUseCaseImpl
import com.mpfcoding.core.usecase.CheckFavoriteUseCase
import com.mpfcoding.core.usecase.CheckFavoriteUseCaseImpl
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
    fun bindGetComicsUseCase(
        useCase: GetCharacterCategoriesUseCaseImpl
    ): GetCharacterCategoriesUseCase

    @Binds
    fun bindCheckFavoriteUseCase(useCase: CheckFavoriteUseCaseImpl): CheckFavoriteUseCase

    @Binds
    fun bindAddFavoriteUseCase(useCase: AddFavoriteUseCaseImpl): AddFavoriteUseCase
}