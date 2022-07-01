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
import kotlin.coroutines.CoroutineContext

class FavoriteUiStateLiveData(
    private val coroutineContext: CoroutineContext,
    private val addFavoriteUseCase: AddFavoriteUseCase
) {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when(it){
                Action.Default -> emit(UiState.Icon(R.drawable.ic_favorite_unchecked))
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

    fun setDefault(){
        action.value = Action.Default
    }

    fun update(detailViewArgs: DetailViewArgs){
        action.value = Action.Update(detailViewArgs)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Icon(@DrawableRes val icon: Int) : UiState()
        data class Erro(@StringRes val messageResId: Int): UiState()
    }

    sealed class Action{
        object Default: Action()
        data class Update(val detailViewArgs: DetailViewArgs): Action()
    }
}