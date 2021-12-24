package com.mpfcoding.core.data.repository

import androidx.paging.PagingSource
import com.mpfcoding.core.domain.model.Character

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>
}