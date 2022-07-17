package com.mpfcoding.app_marvel.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mpfcoding.core.data.DbConstants.CHARACTERS_COLUMN_INFO_ID
import com.mpfcoding.core.data.DbConstants.CHARACTERS_COLUMN_INFO_IMAGE_URL
import com.mpfcoding.core.data.DbConstants.CHARACTERS_COLUMN_INFO_NAME
import com.mpfcoding.core.data.DbConstants.CHARACTERS_TABLE_NAME

@Entity(tableName = CHARACTERS_TABLE_NAME)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val autoId: Int = 0,
    @ColumnInfo(name = CHARACTERS_COLUMN_INFO_ID)
    val id: Int,
    @ColumnInfo(name = CHARACTERS_COLUMN_INFO_NAME)
    val name: String,
    @ColumnInfo(name = CHARACTERS_COLUMN_INFO_IMAGE_URL)
    val imageUrl: String
)