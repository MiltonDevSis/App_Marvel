package com.mpfcoding.core.data.repository

import com.mpfcoding.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoritesLocalDataSource {

    fun getAll(): Flow<List<Character>>

    fun isFavorite(characterId: Int): Boolean

    suspend fun save(character: Character)

    suspend fun delete(character: Character)
}