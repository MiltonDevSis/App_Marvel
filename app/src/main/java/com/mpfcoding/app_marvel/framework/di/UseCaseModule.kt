package com.mpfcoding.app_marvel.framework.di

import com.mpfcoding.core.usecase.AddFavoriteUseCase
import com.mpfcoding.core.usecase.AddFavoriteUseCaseImpl
import com.mpfcoding.core.usecase.CheckFavoriteUseCase
import com.mpfcoding.core.usecase.CheckFavoriteUseCaseImpl
import com.mpfcoding.core.usecase.GetCharacterCategoriesUseCase
import com.mpfcoding.core.usecase.GetCharacterCategoriesUseCaseImpl
import com.mpfcoding.core.usecase.GetCharacterSortingUseCase
import com.mpfcoding.core.usecase.GetCharactersSortingUseCaseImpl
import com.mpfcoding.core.usecase.GetCharactersUseCase
import com.mpfcoding.core.usecase.GetCharactersUseCaseImpl
import com.mpfcoding.core.usecase.GetFavoritesUseCase
import com.mpfcoding.core.usecase.GetFavoritesUseCaseImpl
import com.mpfcoding.core.usecase.RemoveFavoriteUseCase
import com.mpfcoding.core.usecase.RemoveFavoriteUseCaseImpl
import com.mpfcoding.core.usecase.SaveCharacterSortingUseCase
import com.mpfcoding.core.usecase.SaveCharacterSortingUseCaseImpl
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

    @Binds
    fun bindRemoveFavoriteUseCase(useCase: RemoveFavoriteUseCaseImpl): RemoveFavoriteUseCase

    @Binds
    fun bindGetFavoriteUseCase(useCase: GetFavoritesUseCaseImpl): GetFavoritesUseCase

    @Binds
    fun bindGetCharactersSortingUseCase(
        useCase: GetCharactersSortingUseCaseImpl
    ): GetCharacterSortingUseCase

    @Binds
    fun bindSaveCharactersSortingUseCase(
        useCase: SaveCharacterSortingUseCaseImpl
    ): SaveCharacterSortingUseCase
}

