package com.mpfcoding.app_marvel.framework.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mpfcoding.app_marvel.framework.network.response.DataWrapperResponse
import com.mpfcoding.app_marvel.framework.network.response.toCharacterModel
import com.mpfcoding.core.data.repository.CharactersRemoteDataSource
import com.mpfcoding.core.domain.model.Character

class CharactersPagingSource(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>,
    private val query: String
): PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
       return try {
            val offset = params.key ?: 0

            val queries = hashMapOf(
                "offset" to offset.toString()
            )

            if(queries.isNotEmpty()){
                queries["nameStartsWith"] = query
            }

            val response = remoteDataSource.fetchCharacters(queries)

            val responseOffset = response.data.offset
            val totalCharacters = response.data.total

            LoadResult.Page(
                data = response.data.results.map { it.toCharacterModel() },
                prevKey = null,
                nextKey = if (responseOffset < totalCharacters){
                    responseOffset + LIMIT
                }else null
            )
        }catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object{
       private const val LIMIT = 20
    }
}