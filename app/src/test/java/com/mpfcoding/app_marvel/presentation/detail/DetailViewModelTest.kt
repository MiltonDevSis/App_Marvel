package com.mpfcoding.app_marvel.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mpfcoding.app_marvel.R
import com.mpfcoding.core.domain.model.Comic
import com.mpfcoding.core.usecase.GetCharacterCategoriesUseCase
import com.mpfcoding.core.usecase.base.ResultStatus
import com.mpfcoding.testing.MainCoroutineRule
import com.mpfcoding.testing.model.CharacterFactory
import com.mpfcoding.testing.model.ComicFactory
import com.mpfcoding.testing.model.EventFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var detailViewModel: DetailViewModel

    @Mock
    private lateinit var getCharactersCategoriesUseCase: GetCharacterCategoriesUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<DetailViewModel.UiState>

    private val character = CharacterFactory().create(CharacterFactory.Hero.TreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(getCharactersCategoriesUseCase)
        detailViewModel.uiState.observeForever(uiStateObserver)
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns success`() =
        runTest {
            // Arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(comics to events))
                )

            // Act
            detailViewModel.getCharacterCategory(character.id)
            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Sucess>())

            val uiStateSucess = detailViewModel.uiState.value as DetailViewModel.UiState.Sucess
            val categoriesParentList = uiStateSucess.detailParentList

            Assert.assertEquals(2, categoriesParentList.size)
            Assert.assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )
            Assert.assertEquals(
                R.string.details_events_category,
                categoriesParentList[1].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only comics`() =
        runTest {
            // Arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(comics to emptyList()))
                )

            // Act
            detailViewModel.getCharacterCategory(character.id)
            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Sucess>())

            val uiStateSucess = detailViewModel.uiState.value as DetailViewModel.UiState.Sucess
            val categoriesParentList = uiStateSucess.detailParentList

            Assert.assertEquals(1, categoriesParentList.size)
            Assert.assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only events`() =
        runTest {
            // Arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(emptyList<Comic>() to events))
                )

            // Act
            detailViewModel.getCharacterCategory(character.id)
            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Sucess>())

            val uiStateSucess = detailViewModel.uiState.value as DetailViewModel.UiState.Sucess
            val categoriesParentList = uiStateSucess.detailParentList

            Assert.assertEquals(1, categoriesParentList.size)
            Assert.assertEquals(
                R.string.details_events_category,
                categoriesParentList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Empty from UiState when get character categories returns an empty result list`() =
        runTest {
            // Arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(emptyList<Comic>() to emptyList()))
                )

            // Act
            detailViewModel.getCharacterCategory(character.id)
            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Empty>())
        }

    @Test
    fun `should notify uiState with Error from UiState when get character categories returns an exception`() =
        runTest {
            // Arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Error(Throwable())
                    )
                )

            // Act
            detailViewModel.getCharacterCategory(character.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Error>())
        }
}