package com.mpfcoding.app_marvel.framework

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.mpfcoding.app_marvel.framework.db.AppDatabase
import com.mpfcoding.app_marvel.framework.paging.CharactersPagingSource
import com.mpfcoding.app_marvel.framework.paging.CharactersRemoteMediator
import com.mpfcoding.core.data.repository.CharactersRemoteDataSource
import com.mpfcoding.core.data.repository.CharactersRepository
import com.mpfcoding.core.domain.model.Character
import com.mpfcoding.core.domain.model.Comic
import com.mpfcoding.core.domain.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharactersRepositoryImpl
@Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val database: AppDatabase
) : CharactersRepository {

    override fun getCachedCharacters(
        query: String,
        orderBy: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Character>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = CharactersRemoteMediator(query, orderBy, database, remoteDataSource)
        ) {
            database.characterDao().pagingSource()
        }.flow.map { pagingData ->
            pagingData.map {
                Character(
                    it.id,
                    it.name,
                    it.imageUrl
                )
            }
        }
    }

    override suspend fun getComics(characterId: Int): List<Comic> {
        return remoteDataSource.fetchComics(characterId)
    }

    override suspend fun getEvents(characterId: Int): List<Event> {
        return remoteDataSource.fetchEvents(characterId)
    }
}