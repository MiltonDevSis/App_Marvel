package com.mpfcoding.app_marvel.framework.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mpfcoding.app_marvel.framework.db.entity.CharacterEntity
import com.mpfcoding.core.data.DbConstants

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(character: List<CharacterEntity>)

    @Query("SELECT * FROM ${DbConstants.CHARACTERS_TABLE_NAME}")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM ${DbConstants.CHARACTERS_TABLE_NAME}")
    suspend fun clearAll()
}