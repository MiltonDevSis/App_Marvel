package com.mpfcoding.core.usecase

import com.mpfcoding.core.data.mapper.SortingMapper
import com.mpfcoding.core.data.repository.StorageRepository
import com.mpfcoding.core.usecase.base.CoroutinesDispatchers
import com.mpfcoding.core.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetCharacterSortingUseCase {
    suspend operator fun invoke(params: Unit = Unit): Flow<Pair<String, String>>
}

class GetCharactersSortingUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository,
    private val sortingMapper: SortingMapper,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, Pair<String, String>>(), GetCharacterSortingUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<Pair<String, String>> {
        return withContext(dispatchers.io()) {
            storageRepository.sorting.map { sorting ->
                sortingMapper.mapToPair(sorting)
            }
        }
    }
}
