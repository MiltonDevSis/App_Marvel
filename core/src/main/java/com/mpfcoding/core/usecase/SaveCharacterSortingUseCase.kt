package com.mpfcoding.core.usecase

import com.mpfcoding.core.data.mapper.SortingMapper
import com.mpfcoding.core.data.repository.StorageRepository
import com.mpfcoding.core.usecase.base.CoroutinesDispatchers
import com.mpfcoding.core.usecase.base.ResultStatus
import com.mpfcoding.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SaveCharacterSortingUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(val sortingPair: Pair<String, String>)
}

class SaveCharacterSortingUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository,
    private val sortingMapper: SortingMapper,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<SaveCharacterSortingUseCase.Params, Unit>(), SaveCharacterSortingUseCase {

    override suspend fun doWork(params: SaveCharacterSortingUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            storageRepository.saveSorting(sortingMapper.mapFromPair(params.sortingPair))
            ResultStatus.Success(Unit)
        }
    }
}