package com.mpfcoding.core.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.mpfcoding.core.domain.model.Character
import com.mpfcoding.core.domain.model.Comic
import com.mpfcoding.core.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>

    fun getCachedCharacters(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Character>>

    suspend fun getComics(characterId: Int): List<Comic>

    suspend fun getEvents(characterId: Int): List<Event>
}