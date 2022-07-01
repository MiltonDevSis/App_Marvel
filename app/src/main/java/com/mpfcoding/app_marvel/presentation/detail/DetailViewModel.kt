package com.mpfcoding.app_marvel.presentation.detail

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpfcoding.app_marvel.R
import com.mpfcoding.app_marvel.presentation.extension.watchStatus
import com.mpfcoding.core.usecase.AddFavoriteUseCase
import com.mpfcoding.core.usecase.GetCharacterCategoriesUseCase
import com.mpfcoding.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    val categories = UiActionStateLiveData(
        coroutinesDispatchers.main(),
        getCharacterCategoriesUseCase
    )

    val favorites = FavoriteUiStateLiveData(
        coroutinesDispatchers.main(),
        addFavoriteUseCase
    )

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    private val _favoriteUiState = MutableLiveData<FavoriteUiState>()
    val favoriteUiState: LiveData<FavoriteUiState> get() = _favoriteUiState

    init {
        _favoriteUiState.value = FavoriteUiState.FavoriteIcon(R.drawable.ic_favorite_unchecked)
    }

    fun getCharacterCategory(characterId: Int) = viewModelScope.launch {
        getCharacterCategoriesUseCase
            .invoke(GetCharacterCategoriesUseCase.GetCategoriesParams(characterId))
            .watchStatus(
                loading = {
                    _uiState.value = UiState.Loading
                },
                sucess = { data ->
                    val detailParentList = mutableListOf<DetailParentVE>()

                    val comics = data.first
                    if (comics.isNotEmpty()) {
                        comics.map {
                            DetailChildVE(it.id, it.imageUrl)
                        }.also {
                            detailParentList.add(
                                DetailParentVE(R.string.details_comics_category, it)
                            )
                        }
                    }

                    val events = data.second
                    if (events.isNotEmpty()) {
                        events.map {
                            DetailChildVE(it.id, it.imageUrl)
                        }.also {
                            detailParentList.add(
                                DetailParentVE(R.string.details_events_category, it)
                            )
                        }
                    }

                    _uiState.value = if (detailParentList.isNotEmpty()) {
                        UiState.Sucess(detailParentList)
                    } else UiState.Empty
                },
                error = {
                    _uiState.value = UiState.Error
                }
            )
    }

    fun updateFavorite(detailViewArgs: DetailViewArgs) = viewModelScope.launch {
        detailViewArgs.run {
            addFavoriteUseCase.invoke(
                AddFavoriteUseCase.Params(characterId, name, imageUrl)
            ).watchStatus(
                loading = {
                    _favoriteUiState.value = FavoriteUiState.Loading
                },
                sucess = {
                    _favoriteUiState.value =
                        FavoriteUiState.FavoriteIcon(R.drawable.ic_favorite_checked)
                },
                error = {

                }
            )
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Sucess(val detailParentList: List<DetailParentVE>) : UiState()
        object Error : UiState()
        object Empty : UiState()
    }

    sealed class FavoriteUiState {
        object Loading : FavoriteUiState()
        class FavoriteIcon(@DrawableRes val icon: Int) : FavoriteUiState()
    }
}