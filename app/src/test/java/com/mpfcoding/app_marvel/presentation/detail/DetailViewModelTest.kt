package com.mpfcoding.app_marvel.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mpfcoding.app_marvel.R
import com.mpfcoding.core.domain.model.Comic
import com.mpfcoding.core.usecase.AddFavoriteUseCase
import com.mpfcoding.core.usecase.CheckFavoriteUseCase
import com.mpfcoding.core.usecase.GetCharacterCategoriesUseCase
import com.mpfcoding.core.usecase.RemoveFavoriteUseCase
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
import org.junit.Assert.assertEquals
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
    private lateinit var addFavoritesUseCase: AddFavoriteUseCase

    @Mock
    private lateinit var checkFavoritesUseCase: CheckFavoriteUseCase

    @Mock
    private lateinit var removeFavoritesUseCase: RemoveFavoriteUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<UiActionStateLiveData.UiState>

    @Mock
    private lateinit var favoriteUiStateObserver: Observer<FavoriteUiStateLiveData.UiState>

    private val character = CharacterFactory().create(CharacterFactory.Hero.TreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(
            getCharactersCategoriesUseCase,
            checkFavoritesUseCase,
            addFavoritesUseCase,
            removeFavoritesUseCase,
            mainCoroutineRule.testDispatcherProvider
        ).apply {
            categories.state.observeForever(uiStateObserver)
            favorites.state.observeForever(favoriteUiStateObserver)
        }
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
            detailViewModel.categories.load(character.id)
            // Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Success>())

            val uiStateSucess =
                detailViewModel.categories.state.value as UiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSucess.detailParentList

            assertEquals(2, categoriesParentList.size)
            assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )
            assertEquals(
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
            detailViewModel.categories.load(character.id)
            // Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Success>())

            val uiStateSucess =
                detailViewModel.categories.state.value as UiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSucess.detailParentList

            assertEquals(1, categoriesParentList.size)
            assertEquals(
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
            detailViewModel.categories.load(character.id)
            // Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Success>())

            val uiStateSucess =
                detailViewModel.categories.state.value as UiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSucess.detailParentList

            assertEquals(1, categoriesParentList.size)
            assertEquals(
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
            detailViewModel.categories.load(character.id)
            // Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Empty>())
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
            detailViewModel.categories.load(character.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Error>())
        }

    @Test
    fun `should notify favorite_uiState with filled favorite icon when check favorite returns true`() =
        runTest {
            // Arrange
            whenever(checkFavoritesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(true)
                    )
                )

            // Action
            detailViewModel.favorites.checkFavorite(character.id)

            // Assert
            verify(favoriteUiStateObserver).onChanged(
                isA<FavoriteUiStateLiveData.UiState.Icon>()
            )
            val uiState =
                detailViewModel.favorites.state.value as FavoriteUiStateLiveData.UiState.Icon

            assertEquals(R.drawable.ic_favorite_checked, uiState.icon)
        }


    @Test
    fun `should notify favorite_uiState with not filled favorite icon when check favorite returns false`() =
        runTest {
            // Arrange
            whenever(checkFavoritesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(false)
                    )
                )

            // Action
            detailViewModel.favorites.checkFavorite(character.id)

            // Assert
            verify(favoriteUiStateObserver).onChanged(
                isA<FavoriteUiStateLiveData.UiState.Icon>()
            )
            val uiState =
                detailViewModel.favorites.state.value as FavoriteUiStateLiveData.UiState.Icon
            assertEquals(R.drawable.ic_favorite_unchecked, uiState.icon)
        }

    @Test
    fun `should notify favorite_uiState with filled favorite icon when current icon is unchecked`() =
        runTest {
            // Arrange
            whenever(addFavoritesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(Unit)
                    )
                )

            // Act
            detailViewModel.run {
                favorites.currentFavoriteIcon = R.drawable.ic_favorite_unchecked
                favorites.update(
                    DetailViewArgs(character.id, character.name, character.imageUrl)
                )
            }

            // Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiStateLiveData.UiState.Icon>())
            val uiState =
                detailViewModel.favorites.state.value as FavoriteUiStateLiveData.UiState.Icon
            assertEquals(R.drawable.ic_favorite_checked, uiState.icon)
        }

    @Test
    fun `should call remove and notify favorite_uiState with filled favorite icon when current icon is checked`() =
        runTest {
            // Arrange
            whenever(removeFavoritesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(Unit)
                    )
                )

            // Act
            detailViewModel.run {
                favorites.currentFavoriteIcon = R.drawable.ic_favorite_checked
                favorites.update(
                    DetailViewArgs(character.id, character.name, character.imageUrl)
                )
            }

            // Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiStateLiveData.UiState.Icon>())
            val uiState =
                detailViewModel.favorites.state.value as FavoriteUiStateLiveData.UiState.Icon
            assertEquals(R.drawable.ic_favorite_unchecked, uiState.icon)
        }
}