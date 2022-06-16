package com.mpfcoding.core.data.repository

import com.mpfcoding.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    suspend fun getAll(): Flow<List<Character>>

    suspend fun saveFavorite(character: Character)

    suspend fun deleteFavorite(character: Character)
}