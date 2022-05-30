package com.mpfcoding.core.usecase

import androidx.paging.PagingConfig
import com.mpfcoding.core.data.repository.CharactersRepository
import com.mpfcoding.testing.MainCoroutineRule
import com.mpfcoding.testing.model.CharacterFactory
import com.mpfcoding.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest{

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Mock
    lateinit var repository: CharactersRepository

    private val hero = CharacterFactory().create(CharacterFactory.Hero.TreeDMan)

    private val fakePAgingSource = PagingSourceFactory().create(listOf(hero))

    @Before
    fun setup(){
        getCharactersUseCase = GetCharactersUseCaseImpl(repository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() = runBlockingTest{

        whenever(repository.getCharacters(""))
            .thenReturn(fakePAgingSource)

        val result = getCharactersUseCase.invoke(
            GetCharactersUseCase.GetCharactersParams("", PagingConfig(20))
        )

        verify(repository).getCharacters("")

        assertNotNull(result.first())
    }
}