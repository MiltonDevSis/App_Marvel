package com.mpfcoding.app_marvel.framework

import androidx.paging.PagingSource
import com.mpfcoding.app_marvel.framework.paging.CharactersPagingSource
import com.mpfcoding.core.data.repository.CharactersRemoteDataSource
import com.mpfcoding.core.data.repository.CharactersRepository
import com.mpfcoding.core.domain.model.Character
import javax.inject.Inject

class CharactersRepositoryImpl
@Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource
) : CharactersRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPagingSource(remoteDataSource, query)
    }
}