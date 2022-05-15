package com.mpfcoding.core.data.repository

import com.mpfcoding.core.domain.model.CharacterPaging
import com.mpfcoding.core.domain.model.Comic
import com.mpfcoding.core.domain.model.Event

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging

    suspend fun fetchComics(characterId: Int): List<Comic>

    suspend fun fetchEvents(characterId: Int): List<Event>

}