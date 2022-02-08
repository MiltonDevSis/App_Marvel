package com.mpfcoding.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mpfcoding.core.data.repository.CharactersRepository
import com.mpfcoding.core.domain.model.Character
import com.mpfcoding.core.usecase.GetCharactersUseCase.GetCharactersParams
import com.mpfcoding.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCharactersUseCase {
    operator fun invoke(params: GetCharactersParams): Flow<PagingData<Character>>

    data class GetCharactersParams(val query: String, val config: PagingConfig)
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : PagingUseCase<GetCharactersParams, Character>(),
    GetCharactersUseCase {

    override fun createFlowObservable(params: GetCharactersParams): Flow<PagingData<Character>> {
        return Pager(config = params.config) {
            charactersRepository.getCharacters(params.query)
        }.flow
    }
}