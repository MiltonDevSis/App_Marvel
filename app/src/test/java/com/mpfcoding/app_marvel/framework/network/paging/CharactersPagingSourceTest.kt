package com.mpfcoding.app_marvel.framework.network.paging

import androidx.paging.PagingSource
import com.mpfcoding.app_marvel.framework.network.response.DataWrapperResponse
import com.mpfcoding.core.data.repository.CharactersRemoteDataSource
import com.mpfcoding.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersPagingSourceTest{

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var charactersPagingSource: CharactersPagingSource

    private lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    @Before
    fun setup(){
        charactersPagingSource = CharactersPagingSource(remoteDataSource, "")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return a sucess load result when load is called`() = runBlockingTest{

        val result = charactersPagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
    }

}