package com.kalanasarange.starwarsplanets.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.kalanasarange.starwarsplanets.MainActivity
import org.junit.Rule
import org.junit.Test

class InstrumentationTest {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    val clickableItemPrefix = "planet_item_"
    val testPlanetName = "Coruscant"
    val planetListScreenTitle = "StarWars Planets"
    val planetDetailsScreenTitle = "Planet Details"


    @Test
    fun testScreenTitle_isDisplayed() {
        composeTestRule.onNodeWithText(planetListScreenTitle).assertIsDisplayed()
    }

    @Test
    fun testScreenPlanetList_isDisplayed() {
        composeTestRule.onNodeWithTag("planet_lazy_column").assertExists()
    }

    @Test
    fun testScreenPlanetList_isDataLoading() {
        // First, verify loading state is shown (Pull-tp-refresh indicator)
        composeTestRule.onNodeWithTag("pull_refresh_indicator").assertIsDisplayed()

        // Wait until content is displayed
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                //Check whether Data has loded to LazyColumn
                composeTestRule.onNodeWithTag(clickableItemPrefix + testPlanetName).assertIsDisplayed()
                true
            } catch (_: AssertionError) {
                false
            }
        }
    }

    @Test
    fun testScreenPlanetList_performClick_isRedirect() {
        // Wait until content is displayed
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                //Check whether Data has loaded to LazyColumn
                composeTestRule.onAllNodesWithTag(clickableItemPrefix, useUnmergedTree = true)
                    .fetchSemanticsNodes().isNotEmpty()

                // Click on a specific item
                composeTestRule.onNodeWithTag(clickableItemPrefix + testPlanetName).performClick()

                // Validate that Planet Details screen opened by checking header title appearance
                composeTestRule.onNodeWithText(planetDetailsScreenTitle).assertIsDisplayed()
                composeTestRule.onNodeWithTag("progress_loader").assertExists()
                true
            } catch (_: AssertionError) {
                false
            }
        }
    }

    @Test
    fun testScreenPlanetDetails_dataLoading() {
        // Wait until content is displayed
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                //Check whether Data has loaded to LazyColumn
                composeTestRule.onAllNodesWithTag(clickableItemPrefix, useUnmergedTree = true)
                    .fetchSemanticsNodes().isNotEmpty()

                // Click on a specific item
                composeTestRule.onNodeWithTag(clickableItemPrefix + testPlanetName).performClick()

                // Wait until planet details load
                composeTestRule.waitUntil(timeoutMillis = 5000) {
                    try{
                //  Check whether Planet title displayed
                        composeTestRule.onNodeWithText(testPlanetName).assertIsDisplayed()
                        true
                    }catch (_: AssertionError){
                        false
                    }
                }
                true
            } catch (_: AssertionError) {
                false
            }
        }
    }
}