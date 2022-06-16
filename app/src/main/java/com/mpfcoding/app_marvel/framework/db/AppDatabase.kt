package com.mpfcoding.app_marvel.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mpfcoding.app_marvel.framework.db.dao.FavoriteDao
import com.mpfcoding.app_marvel.framework.db.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}