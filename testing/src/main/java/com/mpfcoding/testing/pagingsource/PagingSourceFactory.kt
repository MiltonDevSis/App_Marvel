package com.mpfcoding.testing.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mpfcoding.core.domain.model.Character

class PagingSourceFactory {

    fun create(hero: List<Character>) = object : PagingSource<Int, Character>() {

        override fun getRefreshKey(state: PagingState<Int, Character>): Int = 1

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
            return LoadResult.Page(
                data = hero,
                prevKey = null,
                nextKey = 20
            )
        }

    }
}