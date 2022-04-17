package com.mpfcoding.app_marvel.framework.remote

import com.mpfcoding.app_marvel.framework.network.MarvelApi
import com.mpfcoding.app_marvel.framework.network.response.DataWrapperResponse
import com.mpfcoding.core.data.repository.CharactersRemoteDataSource
import javax.inject.Inject

class RetrofitCharactersDataSource @Inject constructor(
    private val marvelApi: MarvelApi
): CharactersRemoteDataSource<DataWrapperResponse> {

    override suspend fun fetchCharacters(queries: Map<String, String>): DataWrapperResponse {
        return marvelApi.getCharacters(queries)
    }
}