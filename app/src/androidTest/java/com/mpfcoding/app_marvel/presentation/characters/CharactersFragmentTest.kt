package com.mpfcoding.app_marvel.presentation.characters

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mpfcoding.app_marvel.launchFragmentInHiltContainer
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
        launchFragmentInHiltContainer<CharactersFragment>()
    }

    @Test
    fun shouldShowCharactersWhenViewIsCreated(){

    }
}