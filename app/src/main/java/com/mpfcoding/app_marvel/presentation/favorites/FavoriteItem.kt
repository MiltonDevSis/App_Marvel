package com.mpfcoding.app_marvel.presentation.favorites

import com.mpfcoding.app_marvel.presentation.common.ListItem

data class FavoriteItem(
    val id: Int,
     val name: String,
    val imageUrl: String,
    override val key: Long = id.toLong()
): ListItem
