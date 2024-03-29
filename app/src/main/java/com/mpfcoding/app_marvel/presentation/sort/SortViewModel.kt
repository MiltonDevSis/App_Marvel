package com.mpfcoding.app_marvel.presentation.sort

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.mpfcoding.app_marvel.presentation.extension.watchStatus
import com.mpfcoding.core.usecase.GetCharacterSortingUseCase
import com.mpfcoding.core.usecase.SaveCharacterSortingUseCase
import com.mpfcoding.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SortViewModel @Inject constructor(
    private val getCharacterSortingUseCase: GetCharacterSortingUseCase,
    private val saveCharacterSortingUseCase: SaveCharacterSortingUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(coroutinesDispatchers.main()) {
            when (action) {
                Action.GetStorageSorting -> {
                    getCharacterSortingUseCase.invoke()
                        .collect { sortingPair ->
                            emit(UiState.SortingResult(sortingPair))
                        }
                }
                is Action.ApplySorting -> {
                    val orderBy = action.orderBy
                    val order = action.order

                    saveCharacterSortingUseCase.invoke(
                        SaveCharacterSortingUseCase.Params(orderBy to order)
                    ).watchStatus(
                        loading = {
                            emit(UiState.ApplyState.Loading)
                        },
                        sucess = {
                            emit(UiState.ApplyState.Success)
                        },
                        error = {
                            emit(UiState.ApplyState.Error)
                        }
                    )
                }
            }
        }
    }

    init {
        action.value = Action.GetStorageSorting
    }

    fun applySorting(orderBy: String, order: String) {
        action.value = Action.ApplySorting(orderBy, order)
    }

    sealed class UiState {
        data class SortingResult(val storageSort: Pair<String, String>) : UiState()

        sealed class ApplyState : UiState() {
            object Loading : ApplyState()
            object Success : ApplyState()
            object Error : ApplyState()
        }
    }

    sealed class Action {
        object GetStorageSorting : Action()
        data class ApplySorting(val orderBy: String, val order: String) : Action()
    }

}