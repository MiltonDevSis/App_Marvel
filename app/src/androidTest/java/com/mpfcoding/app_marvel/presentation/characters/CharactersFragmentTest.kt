package com.mpfcoding.app_marvel.presentation.characters

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CharactersFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        launchFragmentInContainer<CharactersFragment>()
    }

    @Test
    fun shouldShowCharactersWhenViewIsCreated(){

    }
}