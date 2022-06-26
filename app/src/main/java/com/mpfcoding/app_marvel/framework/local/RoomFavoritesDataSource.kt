package com.mpfcoding.app_marvel.framework.local

import com.mpfcoding.app_marvel.framework.db.dao.FavoriteDao
import com.mpfcoding.app_marvel.framework.db.entity.FavoriteEntity
import com.mpfcoding.app_marvel.framework.db.entity.toCharacterModel
import com.mpfcoding.core.data.repository.FavoritesLocalDataSource
import com.mpfcoding.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomFavoritesDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao
): FavoritesLocalDataSource {

    override fun getAll(): Flow<List<Character>> {
        return favoriteDao.loadFavorites().map {
            it.toCharacterModel()
        }
    }

    override suspend fun save(character: Character) {
        favoriteDao.insertFavorite(character.toFavoriteEntity())
    }

    override suspend fun delete(character: Character) {
        favoriteDao.deleteFavorite(character.toFavoriteEntity())
    }

    private fun Character.toFavoriteEntity() = FavoriteEntity(id, name, imageUrl)
}