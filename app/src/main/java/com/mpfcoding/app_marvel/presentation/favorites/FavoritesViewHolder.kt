package com.mpfcoding.app_marvel.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mpfcoding.app_marvel.databinding.ItemCharacterBinding
import com.mpfcoding.app_marvel.framework.imageloader.ImageLoader
import com.mpfcoding.app_marvel.presentation.common.GenericViewHolder

class FavoritesViewHolder(
    itemBingeing: ItemCharacterBinding,
    private val imageLoader: ImageLoader
) : GenericViewHolder<FavoriteItem>(itemBingeing) {

    private val textName: TextView = itemBingeing.textName
    private val imageCharacter: ImageView = itemBingeing.imageCharacter

    override fun bind(data: FavoriteItem) {
        textName.text = data.name
        imageLoader.load(imageCharacter, data.imageUrl)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): FavoritesViewHolder {
            val itemBingeing = ItemCharacterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return FavoritesViewHolder(itemBingeing, imageLoader)
        }
    }
}