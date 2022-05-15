package com.mpfcoding.app_marvel.framework.remote

import com.mpfcoding.app_marvel.framework.network.MarvelApi
import com.mpfcoding.app_marvel.framework.network.response.toCharacterModel
import com.mpfcoding.app_marvel.framework.network.response.toComicModel
import com.mpfcoding.app_marvel.framework.network.response.toEventModel
import com.mpfcoding.core.data.repository.CharactersRemoteDataSource
import com.mpfcoding.core.domain.model.CharacterPaging
import com.mpfcoding.core.domain.model.Comic
import com.mpfcoding.core.domain.model.Event
import javax.inject.Inject

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource {

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

    override suspend fun fetchComics(characterId: Int): List<Comic> {
        return marvelApi.getComics(characterId).data.results.map {
            it.toComicModel()
        }
    }

    override suspend fun fetchEvents(characterId: Int): List<Event> {
        return marvelApi.getEvents(characterId).data.results.map {
            it.toEventModel()
        }
    }
}