package com.mpfcoding.app_marvel.presentation.extension

import com.mpfcoding.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<ResultStatus<T>>.watchStatus(
    loading: () -> Unit = {},
    sucess: (data: T) -> Unit,
    error: (throwable: Throwable) -> Unit
){
    collect { status ->
        when(status){
            ResultStatus.Loading -> loading.invoke()
            is ResultStatus.Success -> sucess.invoke(status.data)
            is ResultStatus.Error -> error.invoke(status.throwable)
        }
    }
}