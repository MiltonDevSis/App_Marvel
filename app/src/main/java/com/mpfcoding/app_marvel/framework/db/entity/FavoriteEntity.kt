package com.mpfcoding.app_marvel.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mpfcoding.core.data.DbConstants.FAVORITES_COLUMN_INFO_ID
import com.mpfcoding.core.data.DbConstants.FAVORITES_COLUMN_INFO_IMAGE_URL
import com.mpfcoding.core.data.DbConstants.FAVORITES_COLUMN_INFO_NAME
import com.mpfcoding.core.data.DbConstants.FAVORITES_TABLE_NAME

/**
 * ColumnInfo serve para caso o proguard ofusque o codigo ainda funcione
 */

@Entity(tableName = FAVORITES_TABLE_NAME)
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = FAVORITES_COLUMN_INFO_ID)
    val id: Int,
    @ColumnInfo(name = FAVORITES_COLUMN_INFO_NAME)
    val name: String,
    @ColumnInfo(name = FAVORITES_COLUMN_INFO_IMAGE_URL)
    val imageUrl: String
)
