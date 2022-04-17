package com.mpfcoding.app_marvel.framework.remote

import com.mpfcoding.app_marvel.framework.network.MarvelApi
import com.mpfcoding.app_marvel.framework.network.response.toCharacterModel
import com.mpfcoding.core.data.repository.CharactersRemoteDataSource
import com.mpfcoding.core.domain.model.CharacterPaging
import javax.inject.Inject

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
): CharactersRemoteDataSource {

    override suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging {
        val data = marvelApi.getCharacters(queries).data
        val characters = data.results.map {
            it.toCharacterModel()
        }
        return CharacterPaging(
            data.offset,
            data.total,
            characters
        )
    }
}