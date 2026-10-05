package com.falin.artspace

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtSpaceScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navigationChangesArtworkAndWrapsInBothDirections() {
        assertArtwork(R.string.mountain_lake, R.string.mountain_lake_description, 1)
        click(R.string.next)
        assertArtwork(R.string.sunset_sea, R.string.sunset_sea_description, 2)
        click(R.string.next)
        assertArtwork(R.string.forest_path, R.string.forest_path_description, 3)
        click(R.string.next)
        assertArtwork(R.string.mountain_lake, R.string.mountain_lake_description, 1)
        click(R.string.previous)
        assertArtwork(R.string.forest_path, R.string.forest_path_description, 3)
        click(R.string.previous)
        assertArtwork(R.string.sunset_sea, R.string.sunset_sea_description, 2)
    }

    @Test
    fun selectedArtworkSurvivesActivityRecreation() {
        click(R.string.next)
        composeRule.activityRule.scenario.recreate()
        assertArtwork(R.string.sunset_sea, R.string.sunset_sea_description, 2)
    }

    private fun click(label: Int) {
        composeRule.onNodeWithText(composeRule.activity.getString(label))
            .performScrollTo().performClick()
    }

    private fun assertArtwork(title: Int, description: Int, position: Int) {
        composeRule.onNodeWithText(composeRule.activity.getString(title))
            .performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.artwork_position, position, 3))
            .performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithContentDescription(composeRule.activity.getString(description))
            .performScrollTo().assertIsDisplayed()
    }
}
