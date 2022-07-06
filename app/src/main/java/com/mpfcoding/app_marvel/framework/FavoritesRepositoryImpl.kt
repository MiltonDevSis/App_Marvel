package com.mpfcoding.app_marvel.framework

import com.mpfcoding.core.data.repository.FavoritesLocalDataSource
import com.mpfcoding.core.data.repository.FavoritesRepository
import com.mpfcoding.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource
) : FavoritesRepository {

    override fun getAll(): Flow<List<Character>> {
        return favoritesLocalDataSource.getAll()
    }

    override fun isFavorite(characterId: Int): Boolean {
        return favoritesLocalDataSource.isFavorite(characterId)
    }

    override suspend fun saveFavorite(character: Character) {
        favoritesLocalDataSource.save(character)
    }

    override suspend fun deleteFavorite(character: Character) {
        favoritesLocalDataSource.delete(character)
    }
}