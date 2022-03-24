package com.mpfcoding.app_marvel.presentation.characters

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mpfcoding.app_marvel.R
import com.mpfcoding.app_marvel.extension.asJsonString
import com.mpfcoding.app_marvel.framework.di.BaseUrlModule
import com.mpfcoding.app_marvel.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@UninstallModules(BaseUrlModule::class)
@HiltAndroidTest
class CharactersFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var server: MockWebServer

    @Before
    fun setup(){
        server = MockWebServer().apply {
            start(8080)
        }
        launchFragmentInHiltContainer<CharactersFragment>()
    }

    @Test
    fun shouldShowCharactersWhenViewIsCreated(){
        server.enqueue(MockResponse().setBody("characters_p1.json".asJsonString()))
        onView(withId(R.id.recycler_character)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown(){
        server.shutdown()
    }
}