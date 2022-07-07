package com.mpfcoding.app_marvel.presentation.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mpfcoding.app_marvel.R
import com.mpfcoding.app_marvel.presentation.extension.watchStatus
import com.mpfcoding.core.usecase.AddFavoriteUseCase
import com.mpfcoding.core.usecase.CheckFavoriteUseCase
import kotlin.coroutines.CoroutineContext

class FavoriteUiStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when (it) {
                is Action.CheckFavorite -> {
                    checkFavoriteUseCase.invoke(
                        CheckFavoriteUseCase.Params(it.characterId)
                    ).watchStatus(
                        sucess = { isFavorite ->
                            var icon = R.drawable.ic_favorite_unchecked
                            if (isFavorite) {
                               icon = R.drawable.ic_favorite_checked
                            }
                            emit(UiState.Icon(icon))
                        },
                        error = {}
                    )
                }
                is Action.Update -> {
                    it.detailViewArgs.run {
                        addFavoriteUseCase.invoke(
                            AddFavoriteUseCase.Params(characterId, name, imageUrl)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            sucess = {
                                emit(UiState.Icon(R.drawable.ic_favorite_checked))
                            },
                            error = {
                                emit(UiState.Erro(R.string.error_add_favorite))
                            }
                        )
                    }
                }
            }
        }
    }

    fun checkFavorite(characterId: Int) {
        action.value = Action.CheckFavorite(characterId)
    }

    fun update(detailViewArgs: DetailViewArgs) {
        action.value = Action.Update(detailViewArgs)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Icon(@DrawableRes val icon: Int) : UiState()
        data class Erro(@StringRes val messageResId: Int) : UiState()
    }

    sealed class Action {
        data class CheckFavorite(val characterId: Int) : Action()
        data class Update(val detailViewArgs: DetailViewArgs) : Action()
    }
}