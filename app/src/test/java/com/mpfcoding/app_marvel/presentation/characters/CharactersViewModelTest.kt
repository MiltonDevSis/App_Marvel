package com.mpfcoding.app_marvel.presentation.characters

import androidx.paging.PagingData
import com.mpfcoding.core.usecase.GetCharactersUseCase
import com.mpfcoding.testing.MainCoroutineRule
import com.mpfcoding.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var charactersViewModel: CharactersViewModel

    private val charactersFactory = CharacterFactory()

    private val pagingDataCharacters = PagingData.from(
        listOf(
            charactersFactory.create(CharacterFactory.Hero.TreeDMan),
            charactersFactory.create(CharacterFactory.Hero.Abomb)
        )
    )

    @Before
    fun setup() {
        charactersViewModel = CharactersViewModel(
            getCharactersUseCase,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun `should validate the paging data object values when calling CharactersPagingData`() =
        runTest {

            whenever(
                getCharactersUseCase.invoke(any())
            ).thenReturn(
                flowOf(pagingDataCharacters)
            )

            val result = charactersViewModel.charactersPagingData("")

            assertNotNull(result.first())
        }

    @ExperimentalCoroutinesApi
    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() =
        runTest {
            whenever(getCharactersUseCase.invoke(any()))
                .thenThrow(RuntimeException())

            charactersViewModel.charactersPagingData("")
        }
}