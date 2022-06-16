package com.mpfcoding.app_marvel.framework.db.dao

import androidx.room.*
import com.mpfcoding.app_marvel.framework.db.entity.FavoriteEntity
import com.mpfcoding.core.data.DbConstants.FAVORITES_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM $FAVORITES_TABLE_NAME")
    suspend fun loadFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)
}