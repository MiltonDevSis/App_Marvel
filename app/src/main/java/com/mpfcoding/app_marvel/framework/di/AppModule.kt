package com.mpfcoding.app_marvel.framework.di

import com.mpfcoding.app_marvel.framework.imageloader.GlideImageLoaderImpl
import com.mpfcoding.app_marvel.framework.imageloader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface AppModule {

    // qual imageLoader eu quero injetar  --> GlideImageLoaderImpl
    // para qual interface --> ImageLoader
    @Binds
    fun bindImageLoader(imageLoader: GlideImageLoaderImpl): ImageLoader
}