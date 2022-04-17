package com.mpfcoding.app_marvel.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {

    fun load(imageView: ImageView, imageUrl: String, @DrawableRes fallback: Int)
}