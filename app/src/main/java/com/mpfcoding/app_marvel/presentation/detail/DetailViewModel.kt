package com.mpfcoding.app_marvel.presentation.detail

import androidx.lifecycle.ViewModel
import com.mpfcoding.core.usecase.AddFavoriteUseCase
import com.mpfcoding.core.usecase.CheckFavoriteUseCase
import com.mpfcoding.core.usecase.GetCharacterCategoriesUseCase
import com.mpfcoding.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase,
    checkFavoriteUseCase: CheckFavoriteUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    val categories = UiActionStateLiveData(
        coroutinesDispatchers.main(),
        getCharacterCategoriesUseCase
    )

    val favorites = FavoriteUiStateLiveData(
        coroutinesDispatchers.main(),
        checkFavoriteUseCase,
        addFavoriteUseCase
    )
}