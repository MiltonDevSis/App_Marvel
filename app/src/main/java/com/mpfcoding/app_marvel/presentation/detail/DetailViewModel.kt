package com.mpfcoding.app_marvel.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpfcoding.core.domain.model.Comic
import com.mpfcoding.core.usecase.GetComicsUseCase
import com.mpfcoding.core.usecase.base.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getComics(characterId: Int) = viewModelScope.launch {
        getComicsUseCase(GetComicsUseCase.GetComicsParams(characterId))
            .watchStatus()
        /**
         * Caso não queira fazer a extention function somente usar direto o collect.
         *
         *  .collect { status ->
                when (status) {
                    ResultStatus.Loading -> UiState.Loading
                    is ResultStatus.Success -> UiState.Sucess(status.data)
                    is ResultStatus.Error -> UiState.Error
                }
            }
         **/

    }

    private fun Flow<ResultStatus<List<Comic>>>.watchStatus() = viewModelScope.launch {
        collect { status ->
            when (status) {
                ResultStatus.Loading -> UiState.Loading
                is ResultStatus.Success -> UiState.Sucess(status.data)
                is ResultStatus.Error -> UiState.Error
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Sucess(val comics: List<Comic>) : UiState()
        object Error : UiState()
    }
}