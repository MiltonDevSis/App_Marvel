package com.mpfcoding.app_marvel.framework.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoaderImpl @Inject constructor(): ImageLoader {

    override fun load(imageView: ImageView, imageUrl: String, fallback: Int) {
        Glide.with(imageView.rootView)
            .load(imageUrl)
            .fallback(fallback)
            .into(imageView)
    }
}