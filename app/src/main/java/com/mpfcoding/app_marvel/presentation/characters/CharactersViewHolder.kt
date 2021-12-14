package com.mpfcoding.app_marvel.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mpfcoding.app_marvel.R
import com.mpfcoding.app_marvel.databinding.ItemCharacterBinding
import com.mpfcoding.core.domain.model.Character

class CharactersViewHolder(
    itemCharacterBinding: ItemCharacterBinding
): RecyclerView.ViewHolder(itemCharacterBinding.root) {

    private val textName = itemCharacterBinding.textName
    private val imageCharacter = itemCharacterBinding.imageCharacter

    fun bind(character: Character){
        textName.text = character.name

        Glide.with(itemView)
            .load(character.imageUrl)
            .fallback(R.drawable.ic_img_loading_error) // caso não venha nenhuma imagem da API, subistitui por essa default
            .into(imageCharacter)
    }

    companion object{
        fun create(parent: ViewGroup): CharactersViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemCharacterBinding.inflate(inflater, parent, false)
            return CharactersViewHolder(itemBinding)
        }
    }
}