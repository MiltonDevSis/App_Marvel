package com.mpfcoding.core.usecase

import com.mpfcoding.core.data.repository.CharactersRepository
import com.mpfcoding.core.domain.model.Comic
import com.mpfcoding.core.usecase.base.ResultStatus
import com.mpfcoding.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetComicsUseCase {

    operator fun invoke(paramns: GetComicsParams): Flow<ResultStatus<List<Comic>>>

    data class GetComicsParams(val characterId: Int)
}

class GetComicsUseCaseImpl @Inject constructor(
    val repository: CharactersRepository
) : GetComicsUseCase, UseCase<GetComicsUseCase.GetComicsParams, List<Comic>>() {

    override suspend fun doWork(
        params: GetComicsUseCase.GetComicsParams
    ): ResultStatus<List<Comic>> {
        val comics = repository.getComics(params.characterId)
        return ResultStatus.Success(comics)
    }
}