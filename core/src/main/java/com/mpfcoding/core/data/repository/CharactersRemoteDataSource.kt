package com.mpfcoding.core.data.repository

import com.mpfcoding.core.domain.model.CharacterPaging

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries: Map<String, String>): CharacterPaging
}