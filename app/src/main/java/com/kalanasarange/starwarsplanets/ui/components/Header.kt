package com.kalanasarange.starwarsplanets.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

/**
 * A composable function that creates a header using Material 3's TopAppBar.
 *
 * This function provides a simple way to create a header with a title and an optional navigation icon.
 *
 * @param title The title text to be displayed in the header.
 * @param navigationIcon A composable lambda for providing a custom navigation icon.
 *                       Defaults to an empty lambda, which means no navigation icon will be shown.
 *
 *  Example Usage:
 *  ```
 *      Header(
 *          title = stringResource(R.string.planet_details_screen_title),
 *          navigationIcon = {
 *              IconButton(onClick = { navController.popBackStack() }) {
 *                  Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
 *              }
 *          }
 *      )
 *  ```
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(title: String, navigationIcon:  @Composable (() -> Unit) = {}) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = navigationIcon
    )
}