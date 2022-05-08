package com.mpfcoding.app_marvel.presentation.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DetailViewArgs(
    val characterId: Int,
    val name: String,
    val imageUrl: String
) : Parcelable
