package com.mpfcoding.app_marvel.presentation.extension

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showShortToast(@StringRes textResID: Int) =
    Toast.makeText(requireContext(), textResID, Toast.LENGTH_SHORT).show()