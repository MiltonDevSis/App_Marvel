package com.mpfcoding.app_marvel.presentation

import android.view.View
import com.mpfcoding.core.domain.model.Character

typealias OnCharacterItemClick = (character: Character, view: View) -> Unit
