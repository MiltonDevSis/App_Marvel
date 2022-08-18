package com.mpfcoding.app_marvel.presentation.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.mpfcoding.app_marvel.R
import com.mpfcoding.app_marvel.databinding.FragmentSortBinding
import com.mpfcoding.core.domain.model.SortingType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSortBinding? = null
    private val binding: FragmentSortBinding get() = _binding!!

    private var orderBy = SortingType.ORDER_BY_NAME.value
    private var order = SortingType.ORDER_ASCENDING.value

    private val viewModel: SortViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSortBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChipGroupListeners()
    }

    private fun setChipGroupListeners() {
        binding.chipGroupOrderBy.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            orderBy =getOrderByValue(chip.id)
        }

        binding.chipGroupOrder.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            order = getOrderValue(chip.id)
        }
    }

    private fun getOrderByValue(chipId: Int): String = when (chipId) {
        R.id.chip_name -> SortingType.ORDER_BY_NAME.value
        R.id.chip_modified -> SortingType.ORDER_BY_MODIFIED.value
        else -> SortingType.ORDER_BY_NAME.value
    }

    private fun getOrderValue(chipId: Int): String = when (chipId) {
        R.id.chip_ascending -> SortingType.ORDER_ASCENDING.value
        R.id.chip_descending -> SortingType.ORDER_DESCENDING.value
        else -> SortingType.ORDER_ASCENDING.value
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}