package com.mpfcoding.app_marvel.framework.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import com.mpfcoding.app_marvel.framework.db.entity.FavoriteEntity
import com.mpfcoding.core.data.DbConstants.FAVORITES_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM $FAVORITES_TABLE_NAME")
    fun loadFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM $FAVORITES_TABLE_NAME WHERE id = :characterId")
    fun hasFavorite(characterId: Int): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)
}