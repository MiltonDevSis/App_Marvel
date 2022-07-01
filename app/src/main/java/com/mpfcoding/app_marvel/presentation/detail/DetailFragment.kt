package com.mpfcoding.app_marvel.presentation.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.mpfcoding.app_marvel.databinding.FragmentDetailBinding
import com.mpfcoding.app_marvel.framework.imageloader.ImageLoader
import com.mpfcoding.app_marvel.presentation.extension.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private val args by navArgs<DetailFragmentArgs>()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewArgs = args.detailViewArgs

        binding.imageCharacter.run {
            transitionName = detailViewArgs.name
            imageLoader.load(this, detailViewArgs.imageUrl)
        }
        setSharedElementTransitionOnEnter()

        loadCategoriesAndObserveUiState(detailViewArgs)
        setAndObserveFavoriteUiState(detailViewArgs)
    }

    private fun loadCategoriesAndObserveUiState(detailViewArgs: DetailViewArgs) {
        viewModel.categories.load(detailViewArgs.characterId)

        viewModel.categories.state.observe(viewLifecycleOwner){ uiState ->
            binding.flipperDetail.displayedChild = when (uiState) {
                UiActionStateLiveData.UiState.Loading -> FLIPPER_CHILD_POSITION_LOADING
                is UiActionStateLiveData.UiState.Success -> {
                    binding.recyclerParentDetail.run {
                        setHasFixedSize(true)
                        adapter = DetailParentAdapter(uiState.detailParentList, imageLoader)
                    }

                    FLIPPER_CHILD_POSITION_DETAIL
                }
                UiActionStateLiveData.UiState.Error -> {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.categories.load(detailViewArgs.characterId)
                    }

                    FLIPPER_CHILD_POSITION_ERROR
                }
                UiActionStateLiveData.UiState.Empty -> FLIPPER_CHILD_POSITION_EMPTY
            }
        }
    }

    private fun setAndObserveFavoriteUiState(detailViewArgs: DetailViewArgs){
        binding.imageFavoriteIcon.setOnClickListener {
           viewModel.favorites.update(detailViewArgs)
        }

        viewModel.favorites.state.observe(viewLifecycleOwner){ uiState ->
            binding.flipperFavorite.displayedChild = when(uiState){
                FavoriteUiStateLiveData.UiState.Loading -> FLIPPER_FAVORITE_CHILD_POSITION_LOADING
                is FavoriteUiStateLiveData.UiState.Icon -> {
                    binding.imageFavoriteIcon.setImageResource(uiState.icon)
                    FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                }
                is FavoriteUiStateLiveData.UiState.Erro -> {
                    showShortToast(uiState.messageResId)
                    FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                }
            }
        }
    }

    // Define a animação da transição como "move"
    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move).apply {
                sharedElementEnterTransition = this
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_DETAIL = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
        private const val FLIPPER_CHILD_POSITION_EMPTY = 3

        private const val FLIPPER_FAVORITE_CHILD_POSITION_IMAGE = 0
        private const val FLIPPER_FAVORITE_CHILD_POSITION_LOADING = 1
    }
}