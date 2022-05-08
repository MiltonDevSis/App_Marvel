package com.mpfcoding.app_marvel.framework.network.response

import com.google.gson.annotations.SerializedName
import com.mpfcoding.core.domain.model.Character

data class CharacterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel(): Character{
    return Character(
        id = this.id,
        name = this.name,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}".replace("http", "https")
    )
}
