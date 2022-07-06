package com.mpfcoding.core.data.repository

import com.mpfcoding.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getAll(): Flow<List<Character>>

    fun isFavorite(characterId: Int): Boolean

    suspend fun saveFavorite(character: Character)

    suspend fun deleteFavorite(character: Character)
}